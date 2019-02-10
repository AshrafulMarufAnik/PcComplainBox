package com.example.jmirza.firebaseauth.activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

   private User userStudent,userPersonnel;

   private TextView nameEt,phoneEt,passwordEt;
   private Button updateBt;
   private FirebaseAuth uAuth;
   private DatabaseReference myRef;
   private FirebaseUser user;
   private String uId;

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

        nameEt=findViewById(R.id.nameUpdateEtID);
        phoneEt=findViewById(R.id.phoneUpdateEtID);
        passwordEt=findViewById(R.id.passwordUpdateEtID);
        updateBt=findViewById(R.id.updateBtID);
        uAuth=FirebaseAuth.getInstance();
        user = uAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        if (uAuth.getCurrentUser()!=null){
            uId = user.getUid();


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(Objects.equals(dataSnapshot.child("student").child(uId).child("occupation").getValue(), "Student")){

                        userStudent =dataSnapshot.child("student").child(uId).getValue(User.class);
                        nameEt.setText(userStudent.name);
                        phoneEt.setText(userStudent.phone);
                        passwordEt.setText(userStudent.password);


                    } else if (Objects.equals(dataSnapshot.child("personnel").child(uId).child("occupation").getValue(), "Personnel")) {


                        userPersonnel =dataSnapshot.child("personnel").child(uId).getValue(User.class);
                        nameEt.setText(userPersonnel.name);
                        phoneEt.setText(userPersonnel.phone);
                        passwordEt.setText(userPersonnel.password);
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

        if (uAuth.getCurrentUser()!=null){

            final String name = nameEt.getText().toString().trim();
            final String phone = phoneEt.getText().toString().trim();
            final String pass = passwordEt.getText().toString().trim();

            uId = user.getUid();


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(Objects.equals(dataSnapshot.child("student").child(uId).child("occupation").getValue(), "Student")){

                        userStudent =dataSnapshot.child("student").child(uId).getValue(User.class);

                        User student = new User(name,userStudent.department,phone,userStudent.email,pass,userStudent.occupation);
                        FirebaseDatabase.getInstance().getReference("student")
                                .child(uAuth.getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(UpdateProfileActivity.this,"Student's profile updated successfully!!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(UpdateProfileActivity.this,ProfileActivity.class));
                                }

                            }
                        });


                    } else if (Objects.equals(dataSnapshot.child("personnel").child(uId).child("occupation").getValue(), "Personnel")) {

                        userPersonnel =dataSnapshot.child("personnel").child(uId).getValue(User.class);
                        User personnel = new User(name,userPersonnel.department,phone,userPersonnel.email,pass,userPersonnel.occupation);
                        FirebaseDatabase.getInstance().getReference("personnel")
                                .child(uAuth.getCurrentUser().getUid()).setValue(personnel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(UpdateProfileActivity.this,"Personnel's profile updated successfully!!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(UpdateProfileActivity.this,ProfileActivity.class));
                                }

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}
