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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.User;
import com.example.jmirza.firebaseauth.viewholder.MyUserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<MyUserViewHolder> {

    private List<User> myUsersList;
    private Context context;
    private Dialog mDialog;

    public UserAdapter(Context context, List<User> myUsersList) {
        this.context = context;
        this.myUsersList = myUsersList;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_row, viewGroup, false);
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

        // dialog ini
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_user);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        myUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userName = mDialog.findViewById(R.id.dialogUserTvID);
                TextView approval = mDialog.findViewById(R.id.dialogApprovalEtID);
                TextView status = mDialog.findViewById(R.id.dialogStatusEtID);
                userName.setText(user.name);
                approval.setText(user.approval);
                status.setText(user.status);
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
