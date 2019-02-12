package com.example.jmirza.firebaseauth.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class MyComplaintsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ComplainAdapter complainAdapter;
    private List<Complaint> myComplaintList;
    private FirebaseAuth uAuth;
    private DatabaseReference myRef;
    private String uId;
    private FirebaseUser user;
    private Complaint userComplaints;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_complaints_my, container, false);
        initialization();
        onClick();

        return view;
    }

    private void initialization() {

        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("complaints");
        toolbar = view.findViewById(R.id.toolbarID);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        myComplaintList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleView);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }


    @Override
    public void onStart() {
        super.onStart();
        myComplaints();
    }

    private void myComplaints() {
        uId = user.getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myComplaintList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userComplaints = postSnapshot.getValue(Complaint.class);
                    final String userID = userComplaints.complainUserId;

                    if (userID.equals(uId)) {
                        myComplaintList.add(userComplaints);
                    }
                }
                complainAdapter = new ComplainAdapter(getContext(), myComplaintList);
                recyclerView.setAdapter(complainAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("My Complaints...");
    }

    private void onClick() {

    }

}

