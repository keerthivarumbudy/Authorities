package com.example.android.authorities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;


public class ComplaintList extends AppCompatActivity {

    private DatabaseReference mRef;
    private DatabaseReference mPoliticianChild;
    private FirebaseDatabase mDatabase;
    private String User;
    private String databaseNode;
    private ListView mListView;
    private NotificationCompat.Builder notification;
    private static final int NotificationId=48574;

    final HashMap<Integer,ArrayList> hashMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        mListView = findViewById(R.id.displayComplaints);
        mDatabase = mDatabase.getInstance();
        mRef = mDatabase.getReference();
        notification= new NotificationCompat.Builder(this);
        Bundle bundle = getIntent().getExtras();
        User = bundle.getString("username");
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

        mRef=mRef.child(databaseNode);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showComplaints(dataSnapshot);
                setNotification();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showComplaints(DataSnapshot dataSnapshot) {

        ArrayList<String> array1 = new ArrayList<>();
        final ArrayList<String> cidlist = new ArrayList<>();
        String num;
        String c;
        Log.d("showComplaints", "entered");
        Integer i=1;
        array1.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            array1.add("Complaint ".toUpperCase() +i);
            c= ds.getKey().toString();
            //cidlist.add(c);
            ArrayList<String> array2 = new ArrayList<>();
            array2.add(c);
            for(DataSnapshot innerDS: ds.getChildren()){

                String complaintStuff=innerDS.getValue().toString();
                num=innerDS.getKey().toString();
                array2.add(num+" - "+complaintStuff);
            }
            hashMap.put(i,array2);
            Log.d("checking hashmap",""+hashMap.get(i));
            array1.add("\n");
            i++;

        }
        Log.d("checking hashmap",""+hashMap.get(1)+hashMap.get(2));


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array1);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position % 2 == 0) {
                    String value = mListView.getItemAtPosition(position).toString();
                    Log.d("check value",value);
                    String[] array3 = value.split(" ");
                    int key = Integer.parseInt(array3[1]);
                    Intent intent1 = new Intent(ComplaintList.this, DisplayComplaint.class);
                    intent1.putStringArrayListExtra("hashMap", hashMap.get(key));
                    //intent1.putExtra("cid",cidlist.get((position/2)-1));
                    intent1.putExtra("username",User);
                    Log.d("check hashmap", ""+hashMap.get(key));
                    ComplaintList.this.startActivity(intent1);

                } else {
                    return;

                }
            }
        });

    }
    public void setNotification(){
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        notification.setTicker("You have a new complaint");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("You have a new complaint");
        notification.setContentText("Read the complaint");
        Intent intent2 =new Intent(this,AuthorityMenu.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager NotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotifyMgr.notify(NotificationId, notification.build());


    }
}
