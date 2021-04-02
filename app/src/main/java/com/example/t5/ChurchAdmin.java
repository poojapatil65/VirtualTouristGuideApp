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

public class ChurchAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText churchName ,country,location,year_establish,church_info;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    Spinner suns1,suns2,suns3,suns4;
    Spinner weeks1,weeks2,weeks3,weeks4;

    String str_name,str_country,str_location,str_year;

    private String time;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Member3");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member3");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        churchName=findViewById(R.id.churchName);
        country=findViewById(R.id.country);
        location=findViewById(R.id.location);
        year_establish=findViewById(R.id.year_establish);
        church_info=findViewById(R.id.church_info);

        suns1=findViewById(R.id.sunhr);
        suns2=findViewById(R.id.sunmi);
        suns3=findViewById(R.id.sunse);
        suns4=findViewById(R.id.sunti);

        weeks1=findViewById(R.id.weekhr);
        weeks2=findViewById(R.id.weekmi);
        weeks3=findViewById(R.id.weekse);
        weeks4=findViewById(R.id.weekti);
        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(ChurchAdmin.this);// context name as per your project name


        suns1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t1=suns1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        suns2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t2=suns2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        suns3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t3=suns3.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        suns4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t4=suns4.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        weeks1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t5=weeks1.getItemAtPosition(i).toString();
                //price_range.setText(t1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        weeks2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t6=weeks2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        weeks3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t7=weeks3.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        weeks4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t8=weeks4.getItemAtPosition(i).toString();
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

                str_name = churchName.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();
                str_year = year_establish.getText().toString();

                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location)
                        && TextUtils.isEmpty(str_year))
                {

                    Toast.makeText(ChurchAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    churchName.setError("Church name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    country.setError("Country is Required");


                }
                if (TextUtils.isEmpty(str_country)) {
                    country .setError("Country is Required");


                }
                if (TextUtils.isEmpty(str_location)) {
                    location .setError("Location is Required");

                }
                if (TextUtils.isEmpty(str_year)) {
                    year_establish .setError("Year establishment is Required");

                }

                if (!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location)
                        && !TextUtils.isEmpty(str_year))
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

                                    time="Mass Time : Sunday : "+t1+": "+t2+": "+t3+" "+t4+"  Weekday : "+t5+": "+t6+": "+t7+" "+t8;
                                    @SuppressWarnings("VisibleForTests")
                                    Member3 imageUploadInfo =
                                            new Member3( churchName.getText().toString().trim() ,"Country : "+country.getText().toString().trim()
                                                    ,"Location : "+location.getText().toString().trim(),"Year establishment : "+year_establish.getText().toString().trim(),
                                                    church_info.getText().toString().trim(),uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(ChurchAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}
