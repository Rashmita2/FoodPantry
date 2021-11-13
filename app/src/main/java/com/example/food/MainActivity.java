package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    Button GotoLoginPage,inventoryDisplay, shopping ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        GotoLoginPage=findViewById(R.id.homelogout);
        inventoryDisplay=findViewById(R.id.homeInventoryBtn);
        shopping = findViewById(R.id.shopping);

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopping_list();
            }
        });

        GotoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }

        });
        inventoryDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Inventory.class));
                finish();
            }

        });
    }

    public void shopping_list() {
        Intent intent = new Intent(this, Main_shoppinglist.class);
        startActivity(intent);
    }


}