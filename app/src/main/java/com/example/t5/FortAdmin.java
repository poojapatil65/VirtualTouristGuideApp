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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Pattern;

public class FortAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText fortName,country,location,timing,fort_info;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    Spinner s1,s2,s3,s4,s5,s6,s7,s8;

    private String time;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;

    String str_name,str_country,str_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fort_admin);

        storageReference = FirebaseStorage.getInstance().getReference("Member4");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member4");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        fortName=findViewById(R.id.fortName);
        country=findViewById(R.id.country);
        location=findViewById(R.id.location);

        fort_info=findViewById(R.id.fort_info);

        s1=findViewById(R.id.fhr);
        s2=findViewById(R.id.fmi);
        s3=findViewById(R.id.fse);
        s4=findViewById(R.id.fti);

        s5=findViewById(R.id.fhr1);
        s6=findViewById(R.id.fmi1);
        s7=findViewById(R.id.fse1);
        s8=findViewById(R.id.fti1);

        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(FortAdmin.this);// context name as per your project name


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
        s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t5=s5.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t6=s6.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t7=s7.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        s8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t8=s8.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

                str_name = fortName.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();


                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location))
                {

                    Toast.makeText(FortAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    fortName.setError("Fort name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    country .setError("Country is Required");
                }
                if (TextUtils.isEmpty(str_location)) {
                    location .setError("Location is Required");

                }

                if (!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location)) {
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

                                    time="Timing "+t1+": "+t2+": "+t3+" "+t4+ " To " +t5+": "+t6+": "+t7+" "+t8;
                                    @SuppressWarnings("VisibleForTests")
                                    Member4 imageUploadInfo =
                                            new Member4(fortName.getText().toString().trim(),"Country : "+country.getText().toString().trim(),
                                                    "Location : "+location.getText().toString().trim(),
                                                    time, fort_info.getText().toString().trim(),
                                                    uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(FortAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}