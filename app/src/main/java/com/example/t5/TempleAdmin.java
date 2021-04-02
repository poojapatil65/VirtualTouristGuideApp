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

public class TempleAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText templeName,country,location,entry,temple_info;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    Spinner tspinner1,tspinner2,tspinner3,tspinner4;
    private String tstr1;
    private String tstr2;
    private String tstr3;
    private String tstr4;
    private String vtime;

    String str_name,str_country,str_location,str_entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Member5");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member5");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        templeName=findViewById(R.id.templeName);
        country=findViewById(R.id.country);
        location=findViewById(R.id.location);

        entry=findViewById(R.id.entry);
        temple_info=findViewById(R.id.temple_info);

        tspinner1=findViewById(R.id.thr);
        tspinner2=findViewById(R.id.tmi);
        tspinner3=findViewById(R.id.tse);
        tspinner4=findViewById(R.id.tti);

        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(TempleAdmin.this);// context name as per your project name

        tspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tstr1=tspinner1.getItemAtPosition(i).toString();
                //price_range.setText(t1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tstr2=tspinner2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tspinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tstr3=tspinner3.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tspinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tstr4=tspinner4.getItemAtPosition(i).toString();
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

                str_name = templeName.getText().toString();
                str_country = country.getText().toString();
                str_location = location.getText().toString();
                str_entry = entry.getText().toString();

                if (TextUtils.isEmpty(str_name) && TextUtils.isEmpty(str_country) && TextUtils.isEmpty(str_location)
                        && TextUtils.isEmpty(str_entry)) {

                    Toast.makeText(TempleAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(str_name)) {
                    templeName.setError("Temple name is Required");

                }
                if (TextUtils.isEmpty(str_country)) {
                    country.setError("Country is Required");
                }
                if (TextUtils.isEmpty(str_location)) {
                    location.setError("Location is Required");
                }
                if (TextUtils.isEmpty(str_entry)) {
                    entry.setError("Entry fees is Required");

                }
                if (!TextUtils.isEmpty(str_name) && !TextUtils.isEmpty(str_country) && !TextUtils.isEmpty(str_location)
                        && !TextUtils.isEmpty(str_entry)) {
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

                                    vtime="Visiting Time : "+tstr1+": "+tstr2+": "+tstr3+" "+tstr4;
                                    @SuppressWarnings("VisibleForTests")
                                    Member5 imageUploadInfo =
                                            new Member5( templeName.getText().toString().trim(),"Country: "+country.getText().toString().trim()
                                                    ,"Location: "+location.getText().toString().trim(),vtime,
                                                    "Entry Fee: "+entry.getText().toString().trim(),temple_info.getText().toString().trim(),uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(TempleAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}
