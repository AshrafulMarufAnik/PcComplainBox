package com.example.jmirza.firebaseauth.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.User;
import com.example.jmirza.firebaseauth.viewholder.MyUserViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<MyUserViewHolder> {

    private List<User> myUsersList;
    private Context context;
    private Dialog mDialog;
    private Spinner spinnerApproval,spinnerStatus;
    private String[] userStatus;
    private DatabaseReference myRef;

    public UserAdapter(Context context, List<User> myUsersList) {
        this.context = context;
        this.myUsersList = myUsersList;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_row, viewGroup, false);
        userStatus = context.getApplicationContext().getResources().getStringArray(R.array.userStatus);
        myRef = FirebaseDatabase.getInstance().getReference("users");
        return new MyUserViewHolder(view);
    }

    // this method is use to bind data and child model view all together
    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder myUserViewHolder, int i) {

        final User user = myUsersList.get(i);

        myUserViewHolder.userName.setText(user.name);
        myUserViewHolder.userPassword.setText(user.password);
        myUserViewHolder.userApproval.setText(user.approval);
        myUserViewHolder.userStatus.setText(user.status);

        // dialog initialization
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_user);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        spinnerApproval = mDialog.findViewById(R.id.approvalSpinnerID);
        spinnerStatus = mDialog.findViewById(R.id.statusSpinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_view, R.id.spinnerTv, userStatus);
        spinnerApproval.setAdapter(adapter);
        spinnerStatus.setAdapter(adapter);

        myUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView userName = mDialog.findViewById(R.id.dialogUserTvID);
                Button saveBt = mDialog.findViewById(R.id.dialogSaveBT);
                userName.setText(user.name);
                if (user.approval.equals("false")) {
                    spinnerApproval.setSelection(0);
                } else if (user.approval.equals("true")) {
                    spinnerApproval.setSelection(1);
                } else if (user.status.equals("true")) {
                    spinnerStatus.setSelection(1);
                } else if (user.status.equals("false")) {
                    spinnerStatus.setSelection(0);
                }
                saveBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userID = user.uId;
                        String approv = spinnerApproval.getSelectedItem().toString();
                        String state = spinnerStatus.getSelectedItem().toString();
                        User editedUser = new User(userID, user.name, user.department, user.phone, user.email, user.password, user.occupation, user.deviceToken, state, approv);

                        if (userID != null) {
                           myRef.child(userID)
                                    .setValue(editedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
                mDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myUsersList.size();
    }


}
