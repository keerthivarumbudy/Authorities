package com.example.android.authorities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayComplaint extends AppCompatActivity {

    private ListView DetailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complaint);
        DetailsList=findViewById(R.id.displayComplaintDetails);
        Intent intent = getIntent();
        ArrayList<String> array1= intent.getStringArrayListExtra("hashMap");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array1);
        DetailsList.setAdapter(adapter);
    }
}
