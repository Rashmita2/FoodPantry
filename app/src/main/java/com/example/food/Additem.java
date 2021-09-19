package com.example.food;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class Additem extends AppCompatActivity {

    EditText pname,pamount,pdoe,prodidnumber;
    ImageView pimage;
    Button okbtn;
    Button takephotobtn;
    Button cancelbtn;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String userID;

    Uri mImageUri;
    String pImageURL;

    FirebaseStorage storage;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);



        pname=findViewById(R.id.additemname);
        pamount=findViewById(R.id.additemnumber);
        pdoe=findViewById(R.id.additemdoe);
        prodidnumber=findViewById(R.id.additempid);
        okbtn=findViewById(R.id.additemconform);
        cancelbtn=findViewById(R.id.additemcancel);

        pimage=findViewById(R.id.additemimage);
        takephotobtn=findViewById(R.id.additemtakephoto);
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        storage=FirebaseStorage.getInstance();
        StorageRef= storage.getReference(userID);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Additem.this,Inventory.class);
                startActivity(intent);
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
                    Toast.makeText(Additem.this,"Please enter name of Product before proceeding",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ImagePicker.Companion.with(Additem.this)
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
                                    launcher.launch(it);
                                } }));

                }

            }

        });


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ProductName = pname.getText().toString();
                String ProductAmount = pamount.getText().toString();
                String ProductDoe = pdoe.getText().toString();
                String ProductIdnumber = prodidnumber.getText().toString();
                String ProductImageUrl = pImageURL;
                if (ProductName.isEmpty()) {
                    pname.setError("Please Enter Product Name");
                    return;
                }
                if (ProductAmount.isEmpty()) {
                    pamount.setError("Please Enter correct number");
                    return;
                }
                if (ProductDoe.isEmpty()) {
                    pdoe.setError("Please Enter DOE");
                    return;
                }
                if ((ProductDoe.length()<10) ){
                    pdoe.setError("Please Enter Date in correct format");
                    return;
                }
                if (ProductIdnumber.isEmpty()) {
                    prodidnumber.setError("Please Enter Product ID");
                    return;
                }
                if (ProductImageUrl==null) {
                    Toast.makeText(Additem.this,"Please upload photo",Toast.LENGTH_SHORT).show();
                }
                else {
                    productinfo addinfo = new productinfo(ProductName, ProductAmount, ProductDoe, ProductIdnumber,ProductImageUrl);
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference(userID);
                    reference.child(ProductName).setValue(addinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Additem.this, "Item has been Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Additem.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), Inventory.class));
                    finish();
                }
            }
        });

    }
}