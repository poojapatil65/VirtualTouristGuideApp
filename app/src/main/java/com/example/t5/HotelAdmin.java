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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Pattern;

public class HotelAdmin extends AppCompatActivity {

    Button btnbrowse1, save;
    EditText hotelName, country, location, price_range, contact;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    TextView checkout_time;
    Spinner s1,s2,s3,s4;

    private String time;
    private String t1;
    private String t2;
    private String t3;
    private String t4;

    String str_name,str_country,str_location,str_price,str_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_admin);


        storageReference = FirebaseStorage.getInstance().getReference("Member");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member");

        btnbrowse1 = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        hotelName = findViewById(R.id.hotelName);
        country = findViewById(R.id.country);
        location = findViewById(R.id.location);
        checkout_time =findViewById(R.id.checkout_time);
        price_range = findViewById(R.id.price_range);
        contact = findViewById(R.id.contact);

        s1=findViewById(R.id.hr);
        s2=findViewById(R.id.mi);
        s3=findViewById(R.id.se);
        s4=findViewById(R.id.ti);
        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(HotelAdmin.this);// context name as per your project name

        btnbrowse1.setOnClickListener(new View.OnClickListener() {
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

                str_name = hotelName.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();
                str_price = price_range.getText().toString();
                str_contact = contact.getText().toString();
                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location)
                        && TextUtils.isEmpty(str_price) && TextUtils.isEmpty(str_contact))
                {

                    Toast.makeText(HotelAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    hotelName.setError("Hotel name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    country .setError("Country is Required");

                }
                if (TextUtils.isEmpty(str_location)) {
                    location .setError("Location is Required");

                }
                if (TextUtils.isEmpty(str_price)) {
                    price_range .setError("Price range is Required");

                }
                if (TextUtils.isEmpty(str_contact)) {
                    contact .setError("Contact is Required");

                }
                if(Pattern.matches("[a-zA-Z]+",contact.getText()) || contact.getText().length()!=10) {

                    contact.setError("Contact number should not contain alphabets and Contact no should be 10 digit ");
                }

                if(!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location)
                        && !TextUtils.isEmpty(str_price) && !TextUtils.isEmpty(str_contact ) && !Pattern.matches("[a-zA-Z]+",contact.getText()) &&
                        contact.getText().length()==10 ) {
                    UploadImage();
                }

            }
        });
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t1=s1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t2=s2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t3=s3.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t4=s4.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                                    time=t1+": "+t2+": "+t3+" "+t4;
                                    @SuppressWarnings("VisibleForTests")
                                    Member imageUploadInfo =
                                            new Member(hotelName.getText().toString().trim(), "Country : "+country.getText().toString().trim(),
                                                    "Location : "+location.getText().toString().trim(),
                                                    "Timing : "+time,"Price range : "+ price_range.getText().toString().trim()+"/-",
                                                    "Contact : "+ contact.getText().toString().trim(), uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(HotelAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}