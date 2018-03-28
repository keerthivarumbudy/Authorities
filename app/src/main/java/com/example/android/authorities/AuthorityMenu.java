
package com.example.android.authorities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthorityMenu extends AppCompatActivity {
    private Button buttonComplaints;
    private Button buttonFeedback;
    private Button buttonResults;
    private Button buttonUpdate;
    private DatabaseReference mRef;
    private DatabaseReference mPoliticianChild;
    private FirebaseDatabase mDatabase;
    private String User;
    private String databaseNode;
    private Toolbar mToolbar;
    private NotificationCompat.Builder notification;
    int NotificationId = 001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_menu);
        Bundle bundle = getIntent().getExtras();
        User = bundle.getString("username");
        notification= new NotificationCompat.Builder(this);
        buttonComplaints= findViewById(R.id.button1);
        buttonFeedback= findViewById(R.id.button2);
        buttonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AuthorityMenu.this, Feedback.class);
                intent2.putExtra("username", User);
                AuthorityMenu.this.startActivity(intent2);
            }
        });
        buttonComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AuthorityMenu.this, ComplaintList.class);
                intent1.putExtra("username", User);
                AuthorityMenu.this.startActivity(intent1);

            }
        });

        mDatabase = mDatabase.getInstance();
        mRef = mDatabase.getReference();
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



        mToolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Authority");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);



        return true;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() ==R.id.main_logout_button){
            Intent i = new Intent(AuthorityMenu.this,MainActivity.class);
            startActivity(i);
        }

        return true;
    }

    public void setNotification(){
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        notification.setTicker("You have a new complaint");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("You have a new complaint");
        notification.setContentText("Read the complaint");
        Intent intent2 =new Intent(this,ComplaintList.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(AuthorityMenu.this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager NotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotifyMgr.notify(NotificationId, notification.build());
    }
}
