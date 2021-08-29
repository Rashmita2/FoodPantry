package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    public static final String TAG="TAG";
    //variables for all input and buttons from register window
    EditText registerFullName, registerEmail,registerPassword,registerConformPassword;
    Button registerUserBtn,gotoLoginBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    //FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //User authentication instance

        //setting variables to the user input and button
        registerFullName=findViewById(R.id.registerFullName);
        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerConformPassword=findViewById(R.id.confPassword);
        registerUserBtn=findViewById(R.id.registerBtn);
        gotoLoginBtn=findViewById(R.id.gotoLoginBtn);

        //firebase initialization

        fAuth=FirebaseAuth.getInstance();
      //  fStore=FirebaseFirestore.getInstance();
       if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        //extracting and validating data from user only when they click on register button
        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //Converting and storing user inputs
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("users");

                String FullName=registerFullName.getText().toString();
                String Email=registerEmail.getText().toString();
                String Password=registerPassword.getText().toString();
                String ConformPassword=registerConformPassword.getText().toString();

                //checking for empty inputs
                if(FullName.isEmpty()){
                    registerFullName.setError("Please Enter Full Name");
                    return;
                }
                if(Email.isEmpty()){
                    registerEmail.setError("Please Enter Email Address");
                    return;
                }
                if(Password.isEmpty()){
                    registerPassword.setError("Please Enter Password");
                    return;
                }
                if(ConformPassword.isEmpty()){
                    registerConformPassword.setError("Please Enter Conformation Password");
                    return;
                }

                //checking for matching password
                if(!Password.equals(ConformPassword)){
                    registerConformPassword.setError("Please Enter Matching Password");
                    return;
                }

                //
                Toast.makeText(Register.this, "Validating Your Information",Toast.LENGTH_SHORT).show();
                signup signuser=new signup(FullName,Email);
                reference.child(FullName).setValue(signuser);

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "You have been Registered",Toast.LENGTH_SHORT).show();
                      //  userID=fAuth.getCurrentUser().getUid();
                    //    DocumentReference documentReference=fStore.collection("users").document(userID);
                       // Map<String,Object> user=new HashMap<>();
                      //  user.put("Name",registerFullName);
                      //  user.put("Email",registerEmail);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Register.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}