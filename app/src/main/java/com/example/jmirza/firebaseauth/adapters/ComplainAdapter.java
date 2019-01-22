package com.example.jmirza.firebaseauth.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.example.jmirza.firebaseauth.viewholder.MyViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ComplainAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Complaint> myComplaintList;
    Context context;

    public ComplainAdapter(Context context,List<Complaint> myComplaintList) {
        this.context=context;
        this.myComplaintList=myComplaintList;
    }


    // onCreateViewHolder method inflate child layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.complaints_row,viewGroup,false);
        MyViewHolder VH=new MyViewHolder(view);

        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        Complaint complaint=myComplaintList.get(i);
        myViewHolder.roomNumber.setText(complaint.roomNo);
        myViewHolder.pcNumber.setText(complaint.pcNumber);
        myViewHolder.details.setText(complaint.description);
        myViewHolder.status.setText(complaint.complainStatus);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(context,myComplaintList.get(i),Toast.LENGTH_LONG).show();

            }
        });



        // set value by position(i) in each viewHolder
       // myViewHolder.roomNumber.setText(complaints.get(i));

        // can handle click listner

    }

    @Override
    public int getItemCount() {
        return myComplaintList.size();
    }

    public  void setSearchOperation(List<Complaint> allmyComplaintList){

        myComplaintList =new ArrayList<>();
        myComplaintList.addAll(allmyComplaintList);
        notifyDataSetChanged();


    }
}
