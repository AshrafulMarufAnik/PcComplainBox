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

public class SolvedComplaintsFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private RecyclerView recyclerView;
    private ComplainAdapter complainAdapter;
    private List<Complaint> solvedComplaintList;
    private DatabaseReference myRef;
    private Complaint solvedComplaints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_complaints_solved, container, false);
        initialization();
        onClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("Solved Complaints...");
    }

    @Override
    public void onStart() {
        super.onStart();
        solvedComplaints();
    }

    private void onClick() {
    }

    private void initialization() {
        myRef = FirebaseDatabase.getInstance().getReference();
        // setting up custom toolbar or actionbar
        toolbar = view.findViewById(R.id.toolbarID);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        recyclerView = view.findViewById(R.id.solvedComplaintsRecycleView);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        solvedComplaintList = new ArrayList<>();

    }

    private void solvedComplaints() {

        myRef = FirebaseDatabase.getInstance().getReference("complaints");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                solvedComplaintList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    solvedComplaints = postSnapshot.getValue(Complaint.class);
                    String comStatus = solvedComplaints.complainStatus;
                    if (comStatus.equals("Solved")) {
                        solvedComplaintList.add(solvedComplaints);
                    }
                }
                complainAdapter = new ComplainAdapter(getContext(), solvedComplaintList);
                recyclerView.setAdapter(complainAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
