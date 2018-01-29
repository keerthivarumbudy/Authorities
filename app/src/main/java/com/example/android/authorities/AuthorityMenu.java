package com.example.android.authorities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AuthorityMenu extends AppCompatActivity {
    private Button buttonComplaints;
    private Button buttonFeedback;
    private Button buttonResults;
    private Button buttonUpdate;
    private String username;
    private Toolbar mToolbar;


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
}
