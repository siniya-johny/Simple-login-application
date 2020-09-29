package com.example.logineg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private Button userLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = firebaseAuth.getInstance();

        userLogout=findViewById(R.id.btLogout);
        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
    }
private void Logout(){
        firebaseAuth.signOut();
        finish();
        Toast.makeText(HomePage.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomePage.this, MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
