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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.User;
import com.example.jmirza.firebaseauth.viewholder.MyUserViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private DatabaseReference myRef;

    public UserAdapter(Context context, List<User> myUsersList) {
        this.context = context;
        this.myUsersList = myUsersList;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_row, viewGroup, false);
        myRef = FirebaseDatabase.getInstance().getReference();
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

        myUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView userName = mDialog.findViewById(R.id.dialogUserTvID);
                final TextView approval = mDialog.findViewById(R.id.dialogApprovalEtID);
                final TextView status = mDialog.findViewById(R.id.dialogStatusEtID);
                Button saveBt = mDialog.findViewById(R.id.dialogSaveBT);
                userName.setText(user.name);
                approval.setText(user.approval);
                status.setText(user.status);
                saveBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userID = user.key;
                        String approv = approval.getText().toString().trim();
                        String state = status.getText().toString().trim();
                        User editedUser= new User(userID,user.name,user.department,user.phone,user.email,user.password,user.occupation,user.deviceToken,state,approv);

                        FirebaseDatabase.getInstance().getReference("users").child(userID)
                                .setValue(editedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                }

                            }
                        });
                    }
                });
                mDialog.show();

               // Toast.makeText(context, user.name, Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myUsersList.size();
    }


}
