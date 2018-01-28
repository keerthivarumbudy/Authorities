package com.example.android.authorities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthorityMenu extends AppCompatActivity {
    private Button buttonComplaints;
    private Button buttonFeedback;
    private Button buttonResults;
    private Button buttonUpdate;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_menu);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        buttonComplaints= findViewById(R.id.button1);
        buttonComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AuthorityMenu.this, ComplaintList.class);
                intent1.putExtra("username", username);
                AuthorityMenu.this.startActivity(intent1);

            }
        });
    }
}
