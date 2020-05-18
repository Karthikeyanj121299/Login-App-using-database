package com.example.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
      private Button logout,vehiclereg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.btnLogout);
        vehiclereg=findViewById(R.id.btnvehreg);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Logout();
            }
        });
        vehiclereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehiclereg();
            }
        });
    }
      private  void Logout(){
          firebaseAuth.signOut();
          finish();
          startActivity(new Intent(SecondActivity.this, MainActivity.class));
      }
    private  void Vehiclereg(){
        startActivity(new Intent(SecondActivity.this, RegistrationActivity2.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu: {
                Logout();
                break;
            }

            case R.id.profileMenu:{
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
