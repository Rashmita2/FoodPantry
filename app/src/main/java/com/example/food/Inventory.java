package com.example.food;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Inventory extends AppCompatActivity implements MyRecyclerViewAdapter.OnInventoryListener {
    Button gotoadd,gotohome;

    Button sortbyid,sortbyamount,sortbyname,sortbydoe;
    FirebaseAuth fAuth;
    String userID;

    private RecyclerView recyclerView;
    private List<Getterforview> mlist;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        recyclerView=findViewById(R.id.recyclerView);
        mlist=new ArrayList<>();



        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(mlist,this,this);
        recyclerView.setAdapter(adapter);



        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                mlist.clear();
                 for(DataSnapshot snapshot1 : snapshot.getChildren()){
                //   Getterforview info = snapshot1.getValue(Getterforview.class);
                     productinfo info = snapshot1.getValue(productinfo.class);

                  assert info != null;
                    String displayid=info.getIdnumber();
                    String displayname=info.getProductName();
                    String displayamount=info.getProductAmount();
                    String displaydate=info.getProductDoe();
                    String displayUrl=info.getProductImageUrl();
                     /*  String displayid=snapshot.child(userID).child(givenname).child("idnumber").getValue(String.class);
                     String displayname=snapshot.child(userID).child(givenname).child("productName").getValue(String.class);
                     String displayamount=snapshot.child(userID).child(givenname).child("productAmount").getValue(String.class);
                     String displaydate=snapshot.child(userID).child(givenname).child("productDoe").getValue(String.class);*/
                     if(displayamount==null){

                     }else if(displayamount==null){

                     } else if(displayname==null){

                     }else if(displayid==null){

                     }
                     else if(displaydate==null){

                     }
                     else{
                         mlist.add(new Getterforview(Integer.valueOf(displayid),displayname,Integer.valueOf(displayamount),displaydate,displayUrl));
                     }

                }

                adapter.notifyDataSetChanged();
                 sortbyid=findViewById(R.id.inventorysortid);
                 sortbyname=findViewById(R.id.inventorysortname);
                 sortbyamount=findViewById(R.id.inventorysortamount);
                 sortbydoe=findViewById(R.id.inventorysortdate);

                sortbyid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(mlist);
                        adapter.notifyDataSetChanged();

                    }
                });
                sortbyname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(mlist,Getterforview.pname);
                        adapter.notifyDataSetChanged();

                    }
                });
                sortbyamount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(mlist,Getterforview.pamount);
                        adapter.notifyDataSetChanged();

                    }
                });
                sortbydoe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Collections.sort(mlist,Getterforview.pdoe);
                        adapter.notifyDataSetChanged();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        gotoadd=findViewById(R.id.inventoryaddbtn);
        gotohome=findViewById(R.id.inventoryhomebtn);

        gotoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Additem.class));
            }
        });

        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }

    @Override
    public void onInventoryClick(int position) {

       // Log.d(TAG,"onInventoryClick:clicked.");
        //mlist.get(position);
        Intent intent=new Intent(this,EditProduct.class);
        intent.putExtra("Product Name",mlist.get(position).getProductName());
        intent.putExtra("Product ID",mlist.get(position).getId().toString());
        intent.putExtra("Product DOE",mlist.get(position).getProductDoe());
        intent.putExtra("Product Amount",mlist.get(position).getProductAmount().toString());
        String newposition=String.valueOf(position);
        intent.putExtra("Product position",newposition);




      //  Log.d(TAG,"onInventoryClick:clicked."+ position);
       startActivity(intent);



    }
}