package com.example.logineg;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {
    /* Define the UI elements */
    private EditText userName, userPassword, userEmail, userAge;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfile;
    String email,name,age,password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth =FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    //upload data to database
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
        }


    private void setupUIViews() {
        userName = (EditText) findViewById(R.id.etRegName);
        userPassword = (EditText) findViewById(R.id.etRegPassword);
        userEmail = (EditText) findViewById(R.id.etRegEmail);
        regButton = (Button) findViewById(R.id.btRegister);
        userLogin = (TextView) findViewById(R.id.tvLogin);
        userAge=findViewById(R.id.etAge);
        userProfile=findViewById(R.id.ivProfile);

    }

    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        age=userAge.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() ||age.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


    private void  sendUserData(){
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(age,email,name);
        myref.setValue(userProfile);
    }
}
