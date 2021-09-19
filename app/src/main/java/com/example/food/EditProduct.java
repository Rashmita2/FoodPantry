package com.example.food;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class EditProduct extends AppCompatActivity {
    FirebaseAuth fAuth;
    String userID;
    DatabaseReference reference;

    DatabaseReference toremovereference;
    EditText pname;
    EditText pamount;
    EditText pdoe;
    EditText prodidnumber;
    EditText newpname, newpamount, newpdoe, newprodidnumber;
    String newedititemposition,newImageUrl;
    ImageView pimage;
    Button takephotobtn;
    int edititemposition;
    private List<Getterforview> mlist;
    //actual database values storage
    String productname;
    Integer productamount;
    Integer productid;
    String productdoe;
    String productimageurl;
    Button updateedititembtn,deleteedititembtn,canceledititembtn;

    Uri mImageUri;
    String pImageURL;

    FirebaseStorage storage;
    StorageReference StorageRef;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edititem);
        pname = findViewById(R.id.edititemname);
        pamount = findViewById(R.id.edititemnumber);
        pdoe = findViewById(R.id.edititemdoe);
        prodidnumber = findViewById(R.id.edititempid);
        pimage=findViewById(R.id.editemimage);
        takephotobtn=findViewById(R.id.newimagebtn);
        updateedititembtn=findViewById(R.id.edititemupdate);
        deleteedititembtn=findViewById(R.id.edititemdelete);
        canceledititembtn=findViewById(R.id.edititemcancel);
        geteditproductinfo();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child(userID);
        storage= FirebaseStorage.getInstance();
        StorageRef= storage.getReference(userID);
        mlist = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    //   Getterforview info = snapshot1.getValue(Getterforview.class);
                    productinfo info = snapshot1.getValue(productinfo.class);

                    assert info != null;
                    String displayid = info.getIdnumber();
                    String displayname = info.getProductName();
                    String displayamount = info.getProductAmount();
                    String displaydate = info.getProductDoe();
                    String displayUrl = info.getProductImageUrl();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri uri = result.getData().getData();
                        pimage.setImageURI(uri);
                        mImageUri = uri;
                        StorageReference riversRef= StorageRef.child(pname.getText().toString());
                        riversRef.putFile(mImageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(),"Failed to Upload",Toast.LENGTH_LONG).show();
                            }
                        });
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pImageURL=uri.toString();
                            }
                        });
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });
        takephotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((pname.getText().toString().isEmpty())){
                    Toast.makeText(EditProduct.this,"Please enter name of Product before proceeding",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ImagePicker.Companion.with(EditProduct.this)
                            .crop()
                            .cropOval()
                            .maxResultSize(512, 512, true)
                            .createIntentFromDialog((Function1) (new Function1() {
                                public Object invoke(Object var1) {
                                    this.invoke((Intent) var1);
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(@NotNull Intent it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    pimage.setImageBitmap(null);
                                    pimage.destroyDrawingCache();
                                    launcher.launch(it);
                                } }));

                }

            }

        });



        updateedititembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpname = findViewById(R.id.edititemname);
                newpamount = findViewById(R.id.edititemnumber);
                newpdoe = findViewById(R.id.edititemdoe);
                newprodidnumber = findViewById(R.id.edititempid);
               edititemupdate(v);
            }
        });

        deleteedititembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteitem(v);
            }
        });
        canceledititembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditProduct.this,Inventory.class);
                startActivity(intent);
            }
        });

    }
    private void deleteitem(View view) {
        productname = mlist.get(edititemposition).getProductName();
        FirebaseDatabase.getInstance().getReference().child(userID).child(productname).removeValue();
        Intent intent=new Intent(this,Inventory.class);
        startActivity(intent);

    }

    private void geteditproductinfo() {
        Log.d(TAG,"ImageURL = FINEE" + getIntent().getStringExtra("URL"));
        if (getIntent().hasExtra("Product Name") && getIntent().hasExtra("Product ID") && getIntent().hasExtra("Product DOE")  && getIntent().hasExtra("Product Amount")&&getIntent().hasExtra("URL")) {

            String productname = getIntent().getStringExtra("Product Name");
            String productid = getIntent().getStringExtra("Product ID");
            String productdoe = getIntent().getStringExtra("Product DOE");
            String productamount = getIntent().getStringExtra("Product Amount");

            String ImageUrl=getIntent().getStringExtra("URL");
            Log.d(TAG,"ImageURL = "+ ImageUrl);
            newedititemposition = getIntent().getStringExtra("Product position");


            edititemposition=Integer.valueOf(newedititemposition);
            displayedititem(productname, productid, productdoe, productamount,ImageUrl);
        }
    }

    private void displayedititem(String productname, String productid, String productdoe, String productamount,String ImageUrl ) {
        // TextView pname,pamount,pdoe,prodidnumber;
        pname.setText(productname);
        pamount.setText(productamount);
        pdoe.setText(productdoe);
        prodidnumber.setText(productid);
       Glide.with(this).load(ImageUrl).into(pimage);;
    }

    private void edititemupdate(View view) {
        nameUpdate() ;
        amountUpdate();
        doeUpdate();
        idUpdate();
        photoUpdate();
        Intent intent=new Intent(this,Inventory.class);
        startActivity(intent);


    }

    private boolean nameUpdate() {
        if (!newpname.getText().toString().equals(mlist.get(edititemposition).getProductName())) {
            productname = mlist.get(edititemposition).getProductName();
            productid=Integer.valueOf(mlist.get(edititemposition).getId());
            productamount=mlist.get(edititemposition).getProductAmount();
            productdoe=mlist.get(edititemposition).getProductDoe();
            productimageurl=mlist.get(edititemposition).getProductImageUrl();
            productinfo addinfo = new productinfo(newpname.getText().toString(), productamount.toString(), productdoe, productid.toString(),productimageurl);

            reference.child(newpname.getText().toString()).setValue(addinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(EditProduct.this, "Item has been Added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProduct.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                }

            });

            Log.d(TAG,"oneedit:clicked = "+ newpname.getText().toString());
            Log.d(TAG,"oneedit:clicked2 = "+ mlist.get(edititemposition).getProductName());
           // reference.child(productname).removeValue();
             FirebaseDatabase.getInstance().getReference().child(userID).child(productname).removeValue();

           // toremovereference.removeValue();
            return true;
        }else {
            return false;
        }

    }

    private boolean amountUpdate() {
        if (!newpamount.getText().toString().equals(mlist.get(edititemposition).getProductAmount())) {
            productname = mlist.get(edititemposition).getProductName();
            Log.d(TAG,"oneedit:clicked = "+ pamount.getText().toString());
            Log.d(TAG,"oneedit:clicked = " + pname.getText().toString());
            Log.d(TAG,"oneedit:clicked = "+ mlist.get(edititemposition).getProductAmount());
            reference.child(productname).child("productAmount").setValue(newpamount.getText().toString());
            return true;
        } else {
            return false;
        }
    }
        private boolean doeUpdate () {
            if (!newpdoe.getText().toString().equals(mlist.get(edititemposition).getProductDoe())) {
                productname = mlist.get(edititemposition).getProductName();
                reference.child(productname).child("productDoe").setValue(newpdoe.getText().toString());
                return true;
            } else {
                return false;
            }
        }
    private boolean photoUpdate () {
        if (!pImageURL.equals(mlist.get(edititemposition).getProductImageUrl())) {
            productname = mlist.get(edititemposition).getProductName();
            reference.child(productname).child("productImageUrl").setValue(pImageURL);
            return true;
        } else {
            return false;
        }
    }
        private boolean idUpdate () {
            if (!newprodidnumber.getText().toString().equals(mlist.get(edititemposition).getId())) {
                productname = mlist.get(edititemposition).getProductName();
                reference.child(productname).child("idnumber").setValue(newprodidnumber.getText().toString());
                return true;
            } else {
                return false;
            }

        }


}
