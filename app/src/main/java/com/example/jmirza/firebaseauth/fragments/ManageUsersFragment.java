package com.example.jmirza.firebaseauth.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.activities.ProfileActivity;
import com.example.jmirza.firebaseauth.adapters.ComplainAdapter;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ManageUsersFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private String[] deptNames;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private ComplainAdapter complainAdapter;
    private List<Complaint> pendingComplaintList;
    private FirebaseAuth uAuth;
    private DatabaseReference myRef;
    private String uId;
    private FirebaseUser user;
    private Complaint pendingComplaints;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_users_manage, container, false);
        initialization();
        onClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("Manage users...");
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
        deptNames = getResources().getStringArray(R.array.deptType);
        spinner = view.findViewById(R.id.spinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_view, R.id.spinnerTv, deptNames);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}

