package com.example.android.authorities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayComplaint extends AppCompatActivity {

    private ListView DetailsList;
    private DatabaseReference mdatabaseRef;
    private String User;
    private String CId;
    private String databaseNode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complaint);
        DetailsList = findViewById(R.id.displayComplaintDetails);
        Intent intent = getIntent();
        ArrayList<String> array1 = intent.getStringArrayListExtra("hashMap");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array1);
        DetailsList.setAdapter(adapter);
        Button AcceptComplaint = findViewById(R.id.acceptComplaint);
        Bundle bundle = getIntent().getExtras();
        User = bundle.getString("username");
        CId= array1.get(0);
        if (User.equals("keerthi")) {
            databaseNode = "Complaints_keerthi_ward1";
            Log.d("user", databaseNode);
        } else if (User.equals("kush")) {
            databaseNode = "Complaints_kush_ward2";
            Log.d("user", databaseNode);
            // mPoliticianChild=mRef.child(databaseNode);
        } else {
            databaseNode = "Complaints_sarkar_ward3";
            Log.d("user", databaseNode);
        }

        mdatabaseRef= FirebaseDatabase.getInstance().getReference(databaseNode);
        Log.d("id",CId);


        AcceptComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mdatabaseRef.child(CId).child("Accepted").setValue("Yes");

            }


        });
    }
}
