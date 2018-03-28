package com.example.android.authorities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.authorities.R;
import com.example.android.authorities.RatingPage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Feedback extends AppCompatActivity {
    private Button bfeedback;
    private EditText Cid;
    private String CId;
    private DatabaseReference database;
    private String acceptValue;
    private ProgressBar feedbackProgressBar;
    private String SubmissionDatestring;
    private String politician;
    private String databaseNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Bundle bundle=getIntent().getExtras();
        politician=bundle.getString("username");
        if (politician.equals("keerthi")) {
            databaseNode = "Complaints_keerthi_ward1";
            Log.d("user", databaseNode);
        } else if (politician.equals("kush")) {
            databaseNode = "Complaints_kush_ward2";
            Log.d("user", databaseNode);
            // mPoliticianChild=mRef.child(databaseNode);
        } else {
            databaseNode = "Complaints_sarkar_ward3";
            Log.d("user", databaseNode);
        }
        feedbackProgressBar=  findViewById(R.id.feedbackProgressBar);
        feedbackProgressBar.setVisibility(View.GONE);
        bfeedback= findViewById(R.id.bfeedback);
        Cid = findViewById(R.id.IdforFeedback);
        bfeedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Check 2", "On click entered");
                CId = Cid.getText().toString().trim();
                Log.d("THE COMPLAINT ID IS",CId);
                database = FirebaseDatabase.getInstance().getReference(databaseNode);
                Log.d("database working?", database.toString());
                feedbackProgressBar.setVisibility(View.VISIBLE);
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        iterateFB(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });

    }

    public void iterateFB(DataSnapshot dataSnapshot) {
        String ref = "nothing";
        outerloop:
        for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
            Log.d("INSIDE SECOND ITERATION", ds2.getKey());
            if (ds2.getKey().trim().equals(CId)) {
                ref = CId.toString();
                Log.d("COMPARED ID ", ds2.getKey() + "  perfectly");
                for (DataSnapshot ds3 : ds2.getChildren()) {
                    if (ds3.getKey().equals("ComplaintDate")) {
                        SubmissionDatestring = ds3.getValue().toString();
                        feedbackProgressBar.setVisibility(View.GONE);
                        break outerloop;
                    }

                }
            }
        }

        if (ref.equals("nothing")) {
            feedbackProgressBar.setVisibility(View.GONE);
            Toast toast = Toast.makeText(this, "This complaint does not exist", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            popupWindow(CId,ref);
        }
    }


    public void popupWindow(String CId, String ref){
        Intent intent=new Intent(Feedback.this,RatingPage.class);
        intent.putExtra("CID",CId);
        intent.putExtra("Politician",ref);
        Feedback.this.startActivity(intent);

    }

}



