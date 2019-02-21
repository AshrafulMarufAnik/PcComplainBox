package com.example.jmirza.firebaseauth.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;

// this method use to find out the child model

public class MyUserViewHolder extends RecyclerView.ViewHolder {

    public TextView userName,userPassword,userApproval,userStatus;

    public MyUserViewHolder(@NonNull View itemView) {
        super(itemView);
        userName=itemView.findViewById(R.id.userNameTV);
        userPassword=itemView.findViewById(R.id.user_passET);
        userApproval=itemView.findViewById(R.id.approvalET);
        userStatus=itemView.findViewById(R.id.status_ET);
    }
}
