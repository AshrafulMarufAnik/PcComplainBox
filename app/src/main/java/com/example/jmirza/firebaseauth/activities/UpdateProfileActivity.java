package com.example.jmirza.firebaseauth.activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jmirza.firebaseauth.R;

import com.example.jmirza.firebaseauth.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameEt, phoneEt, passwordEt;
    private Button updateBt;
    private FirebaseAuth uAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String uId;
    private User UserInfo;
    private static final String TAG = "UpdateProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setTitle("UPDATE your profile");
        initialization();
        onClick();
    }

    private void onClick() {
        updateBt.setOnClickListener(this);
    }

    private void initialization() {

        nameEt = findViewById(R.id.nameUpdateEtID);
        phoneEt = findViewById(R.id.phoneUpdateEtID);
        passwordEt = findViewById(R.id.passwordUpdateEtID);
        updateBt = findViewById(R.id.updateBtID);
        uAuth = FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();

        if (user != null) {
            uId = user.getUid();
            myRef = FirebaseDatabase.getInstance().getReference("users").child(uId);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInfo = dataSnapshot.getValue(User.class);
                    if (UserInfo != null) {
                        final String userName = UserInfo.name;
                        final String userPhone = UserInfo.phone;
                        final String userPassword = UserInfo.password;
                        nameEt.setText(userName);
                        phoneEt.setText(userPhone);
                        passwordEt.setText(userPassword);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateBtID:
                updateProfile();
                break;
        }
    }

    private void updateProfile() {

        if (uAuth.getCurrentUser() != null) {

            final String name = nameEt.getText().toString().trim();
            final String phone = phoneEt.getText().toString().trim();
            final String pass = passwordEt.getText().toString().trim();

            myRef.addValueEventListener(new ValueEventListener() {
                private User userInfo;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    this.userInfo = dataSnapshot.getValue(User.class);
                    User user1 = new User(userInfo.uId, name, userInfo.department, phone, userInfo.email,
                            pass, userInfo.occupation, userInfo.deviceToken,
                            userInfo.status, userInfo.approval);
                    FirebaseDatabase.getInstance().getReference("users").child(uId).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (user != null) {
                                    user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(UpdateProfileActivity.this, "Your profile updated successfully!!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(UpdateProfileActivity.this, ProfileActivity.class));
                                            }
                                        }
                                    });
                                }


                            }

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
