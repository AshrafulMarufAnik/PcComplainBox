package com.example.jmirza.firebaseauth.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.activities.ProfileActivity;

public class SolvedComplaintsFragment extends Fragment {

    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_complaints_solved,container,false);
        initialization();
        onClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("Solved Complaints...");
    }

    private void onClick() {
    }

    private void initialization() {
    }
}
