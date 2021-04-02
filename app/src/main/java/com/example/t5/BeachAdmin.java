package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
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

public class BeachAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText mbeachName, mcountry, mstate, mideal_trip_duration, minfo;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    Spinner spinner1,spinner2;
    String m1,m2,month;

    String str_name,str_country,str_location,str_duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Member2");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member2");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        mbeachName = findViewById(R.id.beachName);
        mcountry= findViewById(R.id.country);
        mstate= findViewById(R.id.state);
        mideal_trip_duration = findViewById(R.id.trip_duration);

        minfo=(EditText) findViewById(R.id.beach_info);

        spinner1=findViewById(R.id.ms1);
        spinner2=findViewById(R.id.ms2);

        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(BeachAdmin.this);// context name as per your project name


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m1=spinner1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m2=spinner2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_name = mbeachName.getText().toString();
                str_country = mcountry.getText().toString();
                str_location = mstate.getText().toString();
                str_duration = mideal_trip_duration.getText().toString();

                if (TextUtils.isEmpty( str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty( str_location)
                        && TextUtils.isEmpty(str_duration))
                {

                    Toast.makeText(BeachAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_name)) {
                    mbeachName.setError("Beach name is Required");
                }
                if (TextUtils.isEmpty(str_country)) {
                    mcountry .setError("Country is Required");


                }
                if (TextUtils.isEmpty(str_location)) {
                    mstate .setError("Location is Required");

                }
                if (TextUtils.isEmpty(str_duration)) {
                    mideal_trip_duration .setError("ideal trip duration is Required");

                }


                if (!TextUtils.isEmpty( str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty( str_location)
                        && !TextUtils.isEmpty(str_duration))
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
                                    month="Best Time To Visit : "+m1+"   To   "+m2;
                                    @SuppressWarnings("VisibleForTests")

                                    Member2 imageUploadInfo =
                                            new Member2(uri.toString(),mbeachName.getText().toString().trim(),"Country : "+mcountry.getText().toString().trim(),"Location : "+mstate.getText().toString().trim(),
                                                    "Ideal trip duration : "+mideal_trip_duration.getText().toString().trim(),month
                                                    ,minfo.getText().toString().trim());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(BeachAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}

