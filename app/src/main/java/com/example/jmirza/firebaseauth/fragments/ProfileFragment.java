package com.example.jmirza.firebaseauth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.activities.ProfileActivity;
import com.example.jmirza.firebaseauth.activities.UpdateProfileActivity;
import com.example.jmirza.firebaseauth.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView profileName, profilePassword, profileEmail, profilePhone;
    private CircleImageView circleImageView;
    private Button editBt;
    private FirebaseAuth uAuth;
    private DatabaseReference myRef;
    private View view;
    private String uId;
    private FirebaseUser user;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private User UserInfo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialization();
        addingProfileDetails();
        onClick();
        return view;

    }

    private void initialization() {
        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        toolbar = view.findViewById(R.id.toolbarID);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        editBt = view.findViewById(R.id.profile_editBT);
        profileName = view.findViewById(R.id.profile_nameEtID);
        profilePassword = view.findViewById(R.id.profile_passwordEtID);
        profileEmail = view.findViewById(R.id.profile_emailEtID);
        profilePhone = view.findViewById(R.id.profile_phoneID);
        circleImageView = view.findViewById(R.id.profile_image);


    }

    private void addingProfileDetails() {

        uId = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("users").child(uId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo = dataSnapshot.getValue(User.class);
                if (UserInfo != null) {
                    String name = UserInfo.name;
                    String password = UserInfo.password;
                    String email = UserInfo.email;
                    String phone = UserInfo.phone;
                    profileName.setText(name);
                    profilePassword.setText(password);
                    profileEmail.setText(email);
                    profilePhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void onClick() {
        editBt.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity) getActivity()).setActionBarTitle("Profile...");
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.profile_editBT) {

            // to call a parent activity just write getActivity....
            startActivity(new Intent(getActivity(), UpdateProfileActivity.class));

        }

    }

}
