package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

public class Main_shoppinglist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shoppinglist);
        final ListView list = findViewById(R.id.list);
        ArrayList<String> foodList = new ArrayList<>();
        foodList.add("Bamboo");
        foodList.add("banana");
        foodList.add("Mango");
        foodList.add("apple");
        foodList.add("Cake");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foodList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Main_shoppinglist.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });
    }
}

