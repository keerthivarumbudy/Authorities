package com.example.android.authorities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mref;
    private EditText UserName;
    private EditText Password;
    private Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        UserName= findViewById(R.id.username);
        Password= findViewById(R.id.password);
        Login= findViewById(R.id.Login);
        mref=FirebaseDatabase.getInstance().getReference();

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent1 = new Intent(Login.this, SignUp.class);
                //Login.this.startActivity(intent1);
                final String username = UserName.getText().toString().trim();
                final String password= Password.getText().toString().trim();
                mref=mref.child("Politician login").child(username);
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String checkPassword = dataSnapshot.getValue(String.class);
                        if(checkPassword.equals(password)){
                            Intent intent1 = new Intent(MainActivity.this, ComplaintList.class);
                            intent1.putExtra("username", username);
                            MainActivity.this.startActivity(intent1);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT);
                            return;
                            //THERE'S A BUG HERE
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT);
                    }


                });



            }
            });
    }
}





