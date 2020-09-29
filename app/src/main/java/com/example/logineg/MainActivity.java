package com.example.logineg;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private  EditText eName;
    private EditText ePassword;
    private TextView eAttemptsInfo;
    private Button eLogin;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
   // private ProgressDialog progressDialog;

    String userName = "";
    String userPassword = "";

    /* Class to hold credentials */


    boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bind the XML views to Java Code Elements */
        eName = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        eAttemptsInfo = findViewById(R.id.tvAttempts);
        eLogin = findViewById(R.id.btLogin);
        userRegistration = findViewById(R.id.tvRegister);



        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }



        /* Describe the logic when the login button is clicked */
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Obtain user inputs */
                userName = eName.getText().toString();
                userPassword = ePassword.getText().toString();

                /* Check if the user inputs are empty */
                if(userName.isEmpty() || userPassword.isEmpty())
                {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(MainActivity.this, "Please enter name and password!", Toast.LENGTH_LONG).show();

                }
                   validate(userName,userPassword);


                }

        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }

    /* Validate the credentials */
    private void validate(String userName, String userPassword) {

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this, HomePage.class));
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    /* Show the attempts remaining */
                    eAttemptsInfo.setText("Attempts Remaining: " + String.valueOf(counter));

                    /* Disable the login button if there are no attempts left */
                    if (counter == 0) {
                        eLogin.setEnabled(false);
                        Toast.makeText(MainActivity.this, "You have used all your attempts try again later!", Toast.LENGTH_LONG).show();
                    }
                    /* Display error message */

                }
            }
        });


    }
}
