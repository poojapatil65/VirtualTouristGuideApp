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

public class HospitalAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText hospital_name, country, location, degree, contact_no;
    TextView time_duration;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    Spinner sp1,sp2,sp3,tsp1,tsp2,tsp3;

    private String time1;
    private String tx1;
    private String tx2;
    private String tx3;
    private String tx4;
    private String tx5;
    private String tx6;

    String str_name,str_country,str_location,str_degree,str_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_admin);

        storageReference = FirebaseStorage.getInstance().getReference("Member1");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member1");

        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        hospital_name = findViewById(R.id.hospital_name);
        country = findViewById(R.id.country);
        location = findViewById(R.id.location);
        degree = findViewById(R.id.degree);
        contact_no = findViewById(R.id.contact_no);
        time_duration = findViewById(R.id.time_duration);
        sp1=findViewById(R.id.hr2);
        sp2=findViewById(R.id.mi2);
        sp3=findViewById(R.id.ti2);

        tsp1=findViewById(R.id.hr3);
        tsp2=findViewById(R.id.mi3);
        tsp3=findViewById(R.id.ti3);
        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(HospitalAdmin.this);// context name as per your project name


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

                str_name = hospital_name.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();
                str_degree = degree.getText().toString();
                str_contact = contact_no.getText().toString();
                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location)
                        && TextUtils.isEmpty(str_degree) && TextUtils.isEmpty(str_contact))
                {

                    Toast.makeText(HospitalAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    hospital_name.setError("Hospital name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    country .setError("Country is Required");
                }
                if (TextUtils.isEmpty(str_location)) {
                    location .setError("Location is Required");

                }
                if (TextUtils.isEmpty(str_degree)) {
                    degree .setError("Degree is Required");

                }
                if (TextUtils.isEmpty(str_contact)) {
                    contact_no .setError("Contact is Required");

                }
                if(Pattern.matches("[a-zA-Z]+",contact_no.getText()) || contact_no.getText().length()!=10) {

                    contact_no.setError("Contact number should not contain alphabets and Contact no should be 10 digit");
                }



                if (!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location)
                        && !TextUtils.isEmpty(str_degree) && !TextUtils.isEmpty(str_contact) && !Pattern.matches("[a-zA-Z]+",contact_no.getText()) && contact_no.getText().length()==10)
                {
                    UploadImage();
                }


            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx1=sp1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx2=sp2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx3=sp3.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tsp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx4=tsp1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tsp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx5=tsp2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tsp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tx6=tsp3.getItemAtPosition(i).toString();
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
                                    time1=tx1+": "+tx2+": "+tx3+"  To  "+tx4+": "+tx5+": "+tx6;
                                    @SuppressWarnings("VisibleForTests")
                                    Member1 imageUploadInfo =
                                            new Member1(hospital_name.getText().toString().trim(), "Country : "+country.getText().toString().trim(),"Location : " +location.getText().toString().trim(),
                                                    "Degree: "+degree.getText().toString().trim(), "Contact No : "+contact_no.getText().toString().trim(),
                                                    "Timing : "+time1, uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(HospitalAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}