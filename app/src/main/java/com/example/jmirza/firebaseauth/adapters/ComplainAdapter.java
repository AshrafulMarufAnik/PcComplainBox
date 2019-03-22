package com.example.jmirza.firebaseauth.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.example.jmirza.firebaseauth.viewholder.MyComplaintViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ComplainAdapter extends RecyclerView.Adapter<MyComplaintViewHolder> {
   private List<Complaint> myComplaintList;
   private Context context;

    public ComplainAdapter(Context context, List<Complaint> myComplaintList) {
        this.context = context;
        this.myComplaintList = myComplaintList;
    }



    // onCreateViewHolder method inflate child layout
    @NonNull
    @Override
    public MyComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.complaints_row, viewGroup, false);
        MyComplaintViewHolder VH = new MyComplaintViewHolder(view);

        return VH;
    }
    // this method is use to put values into all views of cardview.
    @Override
    public void onBindViewHolder(@NonNull MyComplaintViewHolder myComplaintViewHolder, final int i) {

        Complaint complaint = myComplaintList.get(i);
        myComplaintViewHolder.roomNumber.setText(complaint.roomNo);
        myComplaintViewHolder.pcNumber.setText(complaint.pcNumber);
        myComplaintViewHolder.details.setText(complaint.description);
        myComplaintViewHolder.status.setText(complaint.complainStatus);
        myComplaintViewHolder.date.setText(complaint.complainDate);

        myComplaintViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public void setSearchOperation(List<Complaint> allmyComplaintList) {
        myComplaintList = new ArrayList<>();
        myComplaintList.addAll(allmyComplaintList);
        notifyDataSetChanged();

    }
}
