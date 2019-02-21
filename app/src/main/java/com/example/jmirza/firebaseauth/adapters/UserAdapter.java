package com.example.jmirza.firebaseauth.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.example.jmirza.firebaseauth.models.User;
import com.example.jmirza.firebaseauth.viewholder.MyUserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<MyUserViewHolder> {

    private List<User> myUsersList;
    private Context context;

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
    // this method use to bind data and child model view together
    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder myUserViewHolder, int i) {

        User user = myUsersList.get(i);
        myUserViewHolder.userName.setText(user.name);
        myUserViewHolder.userPassword.setText(user.password);
        myUserViewHolder.userApproval.setText(user.approval);
        myUserViewHolder.userStatus.setText(user.status);

        myUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(context,myComplaintList.get(i),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myUsersList.size();
    }


}
