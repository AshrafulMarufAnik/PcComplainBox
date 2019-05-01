package com.example.jmirza.firebaseauth.activities;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.adapters.ComplainAdapter;
import com.example.jmirza.firebaseauth.models.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mStartTime;
    private EditText mEndTime;
    private DatePickerDialogFragment mDatePickerDialogFragment;
    private RecyclerView recyclerView;


    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search Complaints");
        initialization();
        onClick();
    }

    private void onClick() {
        mStartTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);

    }

    private void initialization() {

        mStartTime = findViewById(R.id.start_date);
        mEndTime = findViewById(R.id.end_date);
        mDatePickerDialogFragment = new DatePickerDialogFragment();
        recyclerView = findViewById(R.id.search_by_date_complaints_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_date) {

            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.end_date) {

            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        }

    }


    public static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;
        private static  String start,end;
        private List<Complaint> allComplaintList = new ArrayList<>();
        private Complaint complaint;
        private ComplainAdapter complainAdapter;

      //  @SuppressLint("ResourceType")
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

           ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Dialog);

            return new DatePickerDialog(context, this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (flag == FLAG_START_DATE) {
                 // mStartTime.setText(format.format(calendar.getTime()));
                start = format.format(calendar.getTime());

            } else if (flag == FLAG_END_DATE) {
                //   mEndTime.setText(format.format(calendar.getTime()));
                end = format.format(calendar.getTime());
            }

           // sortByDate(start,end);

        }

     /*   private void sortByDate(String start, String end) {

            FirebaseDatabase.getInstance().getReference("complaints").orderByChild("complainDate").startAt(start).endAt(end).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        complaint = postSnapshot.getValue(Complaint.class);
                        allComplaintList.add(complaint);
                    }
                    complainAdapter = new ComplainAdapter(getContext(), allComplaintList);
                    recyclerView.setAdapter(complainAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }*/
    }
}

