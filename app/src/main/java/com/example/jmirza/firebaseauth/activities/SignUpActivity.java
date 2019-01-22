package com.example.jmirza.firebaseauth.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.Personnel;
import com.example.jmirza.firebaseauth.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText nameEt, genderEt, phoneEt, emailEt, passEt;
    TextView verifiedEmailEt;
    ProgressBar progressBar;
    Button registerButton;
    TextView loginTextView;
    RadioGroup radioGroup;
    String userType;
    private FirebaseAuth uAuth;
    private DatabaseReference userTokenRef;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        initialization();
        onClick();
    }

    public void initialization() {
        radioGroup = findViewById(R.id.userTypeRgId);
        nameEt = findViewById(R.id.nameEtId);
        genderEt = findViewById(R.id.genderEtId);
        phoneEt = findViewById(R.id.phoneEtId);
        emailEt = findViewById(R.id.emailEtId);
        passEt = findViewById(R.id.passwordEtId);
        registerButton = findViewById(R.id.registerBtnId);
        loginTextView = findViewById(R.id.signInTvId);
        String text = "Already have an account? <font color='red'>Login</font>";
        loginTextView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        progressBar = findViewById(R.id.progressBarID);
        uAuth = FirebaseAuth.getInstance();
        userTokenRef = FirebaseDatabase.getInstance().getReference("user_token");
        verifiedEmailEt = findViewById(R.id.versificationTV);

    }

    public void onClick() {
        registerButton.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (uAuth.getCurrentUser() != null) {

            if (uAuth.getCurrentUser().isEmailVerified()) {
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtnId:
                registerUser();
                break;
            case R.id.signInTvId:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    public void registerUser() {

        final String name = nameEt.getText().toString().trim();
        final String gender = genderEt.getText().toString().trim();
        final String phone = phoneEt.getText().toString().trim();
        final String email = emailEt.getText().toString().trim();
        final String pass = passEt.getText().toString().trim();
        if (checkValidity()) {
            //toast

        } else {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Toast.makeText(SignUpActivity.this, "Select a type to register", Toast.LENGTH_LONG).show();
            } else {
                // one of the radio buttons is checked
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                this.userType = (String) radioButton.getText();

                if (id == R.id.studentRbId) {
                    progressBar.setVisibility(View.VISIBLE);
                    final String occupation = "Student";
                    uAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                userTokenRef.child("student").setValue(deviceToken);
                                String userId = uAuth.getCurrentUser().getUid();
                                Student student = new Student(userId,name, gender, phone, email, pass, occupation, deviceToken);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(uAuth.getCurrentUser().getUid()).setValue(student)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    uAuth.getCurrentUser().sendEmailVerification()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getApplicationContext(), "Student registered succesfully!..Check your email for verification..", Toast.LENGTH_LONG).show();
                                                                        finish();
                                                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(intent);

                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Student registration failed! try again.." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                } else if (id == R.id.personnelRbId) {
                    // personnel reg
                    progressBar.setVisibility(View.VISIBLE);
                    final String occupation = "Personnel";

                    uAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                userTokenRef.child("personnel").setValue(deviceToken);
                                String userId = uAuth.getCurrentUser().getUid();
                                Personnel personnel = new Personnel(userId, name, gender, phone, email, pass, occupation, deviceToken);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(uAuth.getCurrentUser().getUid()).setValue(personnel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.GONE);

                                                if (task.isSuccessful()) {
                                                    uAuth.getCurrentUser().sendEmailVerification()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getApplicationContext(), "Personnel registered succesfully!..Check your email for verification..", Toast.LENGTH_LONG).show();
                                                                        finish();
                                                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(intent);
                                                                    }
                                                                }
                                                            });

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Personnel registration failed! try again.." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });


                }

            }

        }
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public boolean checkValidity() {
        boolean cancel = false;

        String name = nameEt.getText().toString().trim();
        String gender = genderEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            cancel = true;
            nameEt.setError("Enter a name");
            nameEt.requestFocus();
        } else if (TextUtils.isEmpty(gender)) {
            cancel = true;
            genderEt.setError("Enter a gender");
            genderEt.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            cancel = true;
            phoneEt.setError("Enter a phone");
            phoneEt.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            cancel = true;
            emailEt.setError("Enter a email");
            emailEt.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            cancel = true;
            passEt.setError("Enter a password");
            passEt.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            cancel = true;
            emailEt.setError("Enter a valid email");
            emailEt.requestFocus();
        }
        return cancel;
    }

}
