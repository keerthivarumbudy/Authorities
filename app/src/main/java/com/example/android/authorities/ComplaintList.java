package com.example.android.authorities;

        import android.nfc.Tag;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Adapter;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;


public class ComplaintList extends AppCompatActivity {

    private DatabaseReference mRef;
    private DatabaseReference mPoliticianChild;
    private FirebaseDatabase mDatabase;
    private String User;
    private String databaseNode;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        mListView = findViewById(R.id.displayComplaints);
        mDatabase = mDatabase.getInstance();
        mRef = mDatabase.getReference();
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

        //getting id's of all children
       /* final List<String> userIdList = new ArrayList();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null)
                    return;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userIdList.add(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error
            }
        });
        final String userID = mRef.child(databaseNode).orderByKey().equalTo(userIdList.get(0)).toString();
*/

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showComplaints(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showComplaints(DataSnapshot dataSnapshot) {
        ArrayList<String> array = new ArrayList<>();
        String key;
        Log.d("showComplaints", "entered");
        Integer i=1;
        array.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            array.add("Complaint"+i);
            i++;

            for(DataSnapshot innerDS: ds.getChildren()){

                String complaintStuff=innerDS.getValue().toString();
                key=innerDS.getKey().toString();
                array.add(key+"-"+complaintStuff);
            }


        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(adapter);

    }
}


