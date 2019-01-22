package com.example.jmirza.firebaseauth.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.activities.ProfileActivity;
import com.example.jmirza.firebaseauth.adapters.ComplainAdapter;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PendingComplaintsFragment extends Fragment {
    public View view;
    Toolbar toolbar;
    TextView toolbarTitle;
    private RecyclerView recyclerView;
    public ComplainAdapter complainAdapter;
    public List<Complaint> pendingComplaintList;
    public FirebaseAuth uAuth;
    public DatabaseReference myRef;
    String uId;
    FirebaseUser user;
    public Complaint pendingComplaints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_complaints_unsolved,container,false);
        initialization();
        onClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("Pending Complaints...");
    }


    private void onClick() {
    }

    private void initialization() {

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        // setting up custom toolbar or actionbar
        toolbar = view.findViewById(R.id.toolbarID);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        recyclerView = view.findViewById(R.id.pending_complaints_recycleView);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingComplaintList = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        pendingComplaints();
    }

    private void pendingComplaints() {
        uId = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("complaint");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pendingComplaintList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        pendingComplaints = snapshot.getValue(Complaint.class);
                        pendingComplaintList.add(pendingComplaints);
                    }
                    complainAdapter = new ComplainAdapter(getContext(), pendingComplaintList);
                    recyclerView.setAdapter(complainAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
