package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Passwordreset extends AppCompatActivity {

    //variables for all input and buttons from Login window
    Button passwordresetBtn;
    EditText EmailAddress;
    //check with database so creating instance
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordreset);

        passwordresetBtn=findViewById(R.id.passwordresetbutton);
        EmailAddress=findViewById(R.id.emailaddress);
        fAuth=FirebaseAuth.getInstance();


        passwordresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.sendPasswordResetEmail(EmailAddress.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Passwordreset.this,"Reset Password link sent to Email address",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(Passwordreset.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}