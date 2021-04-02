package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Pattern;

public class MarketAdmin extends AppCompatActivity {
    Button btnbrowse, save;
    EditText marketName,country,location,buy,market_info;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    String str_name,str_country,str_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Member6");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member6");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        marketName=findViewById(R.id.marketName);
        country=findViewById(R.id.country);
        location=findViewById(R.id.location);
        buy=findViewById(R.id.buy);
        market_info=findViewById(R.id.market_info);

        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(MarketAdmin.this);// context name as per your project name


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

                str_name = marketName.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();

                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location))
                {

                    Toast.makeText(MarketAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    marketName.setError("Market name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    country .setError("Country is Required");
                }
                if (TextUtils.isEmpty(str_location)) {
                    location .setError("Location is Required");

                }


                if (!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location))
                {
                    UploadImage();
                }



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
                imgview2.setImageBitmap(bitmap);
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

            progressDialog.setTitle("Data is Uploading...");
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
                                    Member6 imageUploadInfo =
                                            new Member6(marketName.getText().toString().trim(),"Country : "+country.getText().toString().trim(),
                                                    "Location : "+location.getText().toString().trim(),
                                                   "Don't Forget to Buy" +buy.getText().toString().trim(),market_info.getText().toString().trim(),uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(MarketAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}