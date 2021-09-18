package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //variables for all input and buttons from Login window
    Button createAccountBtn,LoginBtn,forgotpasswordBtn;
    EditText EmailAddress,Password;
    //check with database so creating instance
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //storing values to variables from user input
        createAccountBtn = findViewById(R.id.createAccountBtn);
        EmailAddress=findViewById(R.id.loginEmail);
        Password=findViewById(R.id.loginPassword);
        LoginBtn=findViewById(R.id.loginBtn);
        forgotpasswordBtn=findViewById(R.id.forgotpasswordBtn);

        //Firebase variable
        fAuth=FirebaseAuth.getInstance();

        //moving user to register page when they click on create account button
        createAccountBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        forgotpasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Passwordreset.class));
            }
        });

        //login user when they click on login button
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get email and password and check if it is empty

                if(EmailAddress.getText().toString().isEmpty()){
                    EmailAddress.setError("Please Enter Email Address");
                    return;
                }

                if(Password.getText().toString().isEmpty()){
                    Password.setError("Please Enter Password");
                    return;
                }

                //Login in User using the Firebase to check email and password
                fAuth.signInWithEmailAndPassword(EmailAddress.getText().toString(),Password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //redirect to Homepage if Login Success
                        Toast.makeText(Login.this,"Welcome Back",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Give Error if Email or Password is wrong
                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    //Default login feature so the user does not have to enter username and password everytime
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}