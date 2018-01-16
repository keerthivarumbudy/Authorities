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


public class ComplaintList extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private String User;
    private String databaseNode;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        mListView= findViewById(R.id.displayComplaints);
        mDatabase=mDatabase.getInstance();
        mRef=mDatabase.getReference();
        Bundle bundle = getIntent().getExtras();
        User = bundle.getString("username");
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
    public void showComplaints(DataSnapshot dataSnapshot){
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            getComplaintInformation ComplaintInfo=new getComplaintInformation();
            if(User.equals("keerthi")){
                databaseNode="Complaints_keerthi_ward1";
            }
            else if(User.equals("kush")){
                databaseNode="Complaints_kush_ward2";
            }
            else{
                databaseNode="Complaints_sarkar_ward3";
            }
            //setting the information
            ComplaintInfo.setComplaintType(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaintType());
            ComplaintInfo.setComplaint(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaint());
            ComplaintInfo.setComplaintDate(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaintDate());
            ComplaintInfo.setComplaintDetails(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaintDetails());
            ComplaintInfo.setComplaintLocation(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaintLocation());
            ComplaintInfo.setComplaintTime(ds.child(databaseNode).getValue(getComplaintInformation.class).getComplaintTime());

            Log.d("data","show data"+ComplaintInfo.getComplaintType());

            ArrayList<String> array= new ArrayList<>();
            array.add(ComplaintInfo.ComplaintType);
            array.add(ComplaintInfo.Complaint);
            array.add(ComplaintInfo.ComplaintDate);
            array.add(ComplaintInfo.ComplaintDetails);
            array.add(ComplaintInfo.ComplaintLocation);
            array.add(ComplaintInfo.ComplaintTime);


            ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        }
    }
}
