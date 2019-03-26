package com.example.jmirza.firebaseauth.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jmirza.firebaseauth.R;

// MyComplaintViewHolder class holds the child view so we need to catch all the values of a child view

public class MyComplaintViewHolder extends RecyclerView.ViewHolder {

   public TextView roomNumber,pcNumber,details,status,date,note;
   public LinearLayout noteLayout;


    public MyComplaintViewHolder(@NonNull View itemView) {
        super(itemView);
        roomNumber=itemView.findViewById(R.id.room_numberTV);
        pcNumber=itemView.findViewById(R.id.pc_numberTV);
        details=itemView.findViewById(R.id.detailsTV);
        status=itemView.findViewById(R.id.statusTV);
        noteLayout=itemView.findViewById(R.id.noteLayoutID);
        note=itemView.findViewById(R.id.noteTV);
        date=itemView.findViewById(R.id.dateTV);
    }
}
