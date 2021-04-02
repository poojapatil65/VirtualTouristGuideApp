package com.example.t5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {

    private TextView e1,e2,e3,e4,e5;
    private ImageView i1;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    Button b1;
    Uri imagePath;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

               i1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        e1=findViewById(R.id.username);
        e2=findViewById(R.id.fullname);
        e3=findViewById(R.id.bio);
        e4=findViewById(R.id.phn_no);
        e5=findViewById(R.id.add);
        b1=findViewById(R.id.update);


        i1=findViewById(R.id.imageView2);

        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();
        reference=database.getReference(firebaseAuth.getUid());



        reference = database.getReference(firebaseAuth.getUid());
       final StorageReference storageReference=firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic").getDownloadUrl().addOnSuccessListener
                (new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // context=firebaseAuth.getCurrentUser().getUid();
                        Picasso.with(getBaseContext()).load(uri).fit().centerCrop().into(i1);
                    }
                });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                e1.setText(user.getUsername());
                e2.setText(user.getEmail());
                e3.setText(user.getFullname());
                e4.setText(user.getPhone_no());
                e5.setText(user.getAddress());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if
                (!validateName() | !validateUserName() | !validateEmail() | !validatePhone() ) {
                    String name = e1.getText().toString();
                    String email = e2.getText().toString();
                    String fullname = e3.getText().toString();
                    String phone = e4.getText().toString();
                    String address = e5.getText().toString();
                    User user = new User(name, fullname, email, phone, address);

                    reference.setValue(user);
                }
                StorageReference imageReference =
                        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic");
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(UpdateProfile.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                    }
                });


                Toast.makeText(UpdateProfile.this,"Successfully Saved",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Pic"), PICK_IMAGE);
            }
        });
    }



    private Boolean validatePhone() {
        String sphone=e4.getText().toString();
        if(sphone.length()>=15){
            e1.setError("UserName to Long");
            return false;

        }
        else {
            e1.setError(null);
            e1.setEnabled(false);
            return true;
        }
    }


    private Boolean validateName(){
        String sfullname=e2.getText().toString();
        if(sfullname.isEmpty()){
            e2.setError("Feild cannot be empty");
            return true;
        }
        else {
            e2.setError(null);
            e2.setEnabled(false);
            return true;
        }
    }
    private Boolean validateUserName(){
        String susername=e1.getText().toString();
        String noWhiteSpace="\\A\\w{4,20}\\z";

        if(susername.isEmpty()){
            e1.setError("Feild cannot be empty");
            return false;
        }
        else if(susername.length()>=15){
            e1.setError("UserName to Long");
            return false;
        }
        else if(!susername.matches(noWhiteSpace)){
            e1.setError("White spaces are not allowed");
            return false;

        }
        else {
            e1.setError(null);
            e1.setEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String semail=e1.getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(semail.isEmpty()){
            e1.setError("Field cannot be empty");
            return false;
        }else  if(!semail.matches(emailPattern)){
            e1.setError("Invalid Email Address");
            return false;
        }
        else {
            e1.setError(null);
            return true;
        }
    }

}
