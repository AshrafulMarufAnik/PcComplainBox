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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.activities.ProfileActivity;
import com.example.jmirza.firebaseauth.adapters.ComplainAdapter;
import com.example.jmirza.firebaseauth.adapters.UserAdapter;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.example.jmirza.firebaseauth.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private String[] deptNames;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> usersList;
    private DatabaseReference myRef;
    private User myUser;


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

        myRef = FirebaseDatabase.getInstance().getReference("users");
        usersList = new ArrayList<>();
        // setting up custom toolbar or actionbar
        toolbar = view.findViewById(R.id.toolbarID);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        recyclerView = view.findViewById(R.id.manage_users_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deptNames = getResources().getStringArray(R.array.departments);
        spinner = view.findViewById(R.id.spinnerID);
    }

    @Override
    public void onStart() {
        super.onStart();
        sortUser();
    }

    private void sortUser() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_view, R.id.spinnerTv, deptNames);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    sortByAll();
                } else if (position == 1) {
                    sortByCSE();
                } else if (position == 2) {
                    sortByEEE();
                } else if (position == 3) {
                    sortByBBA();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sortByAll() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myUser = dataSnapshot1.getValue(User.class);
                    if (myUser != null) {
                        usersList.add(myUser);
                    }
                }
                userAdapter = new UserAdapter(getContext(), usersList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sortByCSE() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myUser = dataSnapshot1.getValue(User.class);
                    if (myUser != null) {
                        String userDept = myUser.department;
                        if (userDept.equals("CSE")) {
                            usersList.add(myUser);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), usersList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sortByEEE() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myUser = dataSnapshot1.getValue(User.class);
                    if (myUser != null) {
                        String userDept = myUser.department;
                        if (userDept.equals("EEE")) {
                            usersList.add(myUser);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), usersList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sortByBBA() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myUser = dataSnapshot1.getValue(User.class);
                    if (myUser != null) {
                        String userDept = myUser.department;
                        if (userDept.equals("BBA")) {
                            usersList.add(myUser);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), usersList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

