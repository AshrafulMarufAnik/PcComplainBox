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

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.example.jmirza.firebaseauth.viewholder.MyComplaintViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ComplainAdapter extends RecyclerView.Adapter<MyComplaintViewHolder> {
    private List<Complaint> myComplaintList;
    private Context context;
    private Spinner spinner;
    private String[] complaintStatus;
    private Dialog mDialog;
    private DatabaseReference myRef;

    public ComplainAdapter(Context context, List<Complaint> myComplaintList) {
        this.context = context;
        this.myComplaintList = myComplaintList;
    }


    // onCreateViewHolder method inflate child layout
    @NonNull
    @Override
    public MyComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.complaints_row, viewGroup, false);
        spinner = view.findViewById(R.id.statusSpinnerID);
       // complaintStatus = context.getResources().getStringArray(R.array.complainStatus);
        complaintStatus = new String[] {"Pending","In Process","Solved","Unsolved"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_view, R.id.spinnerTv, complaintStatus);
        spinner.setAdapter(adapter);
        myRef = FirebaseDatabase.getInstance().getReference();
        MyComplaintViewHolder VH = new MyComplaintViewHolder(view);


        return VH;
    }

    // this method is use to put values into all views of cardview.
    @Override
    public void onBindViewHolder(@NonNull MyComplaintViewHolder myComplaintViewHolder, final int i) {

        final Complaint complaint = myComplaintList.get(i);
        final String status = complaint.complainStatus.toLowerCase();
        if (status.equals("pending")) {
            myComplaintViewHolder.roomNumber.setText(complaint.roomNo);
            myComplaintViewHolder.pcNumber.setText(complaint.pcNumber);
            myComplaintViewHolder.details.setText(complaint.description);
            myComplaintViewHolder.status.setText(complaint.complainStatus);
            myComplaintViewHolder.noteLayout.setVisibility(View.GONE);
            myComplaintViewHolder.date.setText(complaint.complainDate);
        } else {
            myComplaintViewHolder.roomNumber.setText(complaint.roomNo);
            myComplaintViewHolder.pcNumber.setText(complaint.pcNumber);
            myComplaintViewHolder.details.setText(complaint.description);
            myComplaintViewHolder.status.setText(complaint.complainStatus);
            myComplaintViewHolder.note.setText(complaint.complainNote);
            myComplaintViewHolder.date.setText(complaint.complainDate);
        }

        // dialog initialization
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_complaint);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        myComplaintViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView complaintUserName = mDialog.findViewById(R.id.dialogComplaintTvID);
                final TextView note = mDialog.findViewById(R.id.dialognoteEtID);
                Button saveBt = mDialog.findViewById(R.id.dialogSaveBT);
                complaintUserName.setText(complaint.complainUserName);
                saveBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String complainID = complaint.complainId;
                        String comStatus = spinner.getSelectedItem().toString().toLowerCase();
                        String comNote = note.getText().toString().trim();
                        if (!comStatus.equals("pending")) {

                            Complaint editedCom = new Complaint(complainID, complaint.complainUserId, complaint.complainUserName,
                                    complaint.complainUserDept, complaint.complainUserDeviceToken, complaint.pcNumber, complaint.roomNo,
                                    complaint.description, complaint.complainStatus, complaint.complainDate, complaint.complainNote);
                            FirebaseDatabase.getInstance().getReference("complaints").child(complainID)
                                    .setValue(editedCom).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        return myComplaintList.size();
    }

    public void setSearchOperation(List<Complaint> allmyComplaintList) {
        myComplaintList = new ArrayList<>();
        myComplaintList.addAll(allmyComplaintList);
        notifyDataSetChanged();

    }
}
