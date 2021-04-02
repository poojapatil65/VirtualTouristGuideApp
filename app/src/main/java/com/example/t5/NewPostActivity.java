package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class NewPostActivity extends AppCompatActivity {

    Button btnbrowse, save;
    EditText info;
    ImageView imgview;
    Uri FilePathUri;
    String t1;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference,sReference;
    DatabaseReference databaseReference,dReference,uidref;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);


        storageReference = FirebaseStorage.getInstance().getReference("UsersPost");
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersPost");




        btnbrowse = (Button) findViewById(R.id.button_choose_image);
        save = (Button) findViewById(R.id.button_upload);

        info=(EditText) findViewById(R.id.edit_text_file_name);
        imgview = (ImageView) findViewById(R.id.image_view);

        progressDialog =
                new ProgressDialog(NewPostActivity.this);// context name as per your project name


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });


    }

//Out side of Oncreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }



    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Please wait...");
            progressDialog.show();
            final StorageReference storageReference2 =
                    storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();
                                    @SuppressWarnings("VisibleForTests")

                                    UsersPost imageUploadInfo =
                                            new UsersPost(uri.toString(),info.getText().toString().trim(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Post Successfullly", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewPostActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            Toast.makeText(NewPostActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


    public void close(View view) {
        Intent intent=new Intent(NewPostActivity.this,Dashboard.class);
        startActivity(intent);
    }
}

