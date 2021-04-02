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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

public class VehicleAdmin extends AppCompatActivity {

    Button btnbrowse, save;
    EditText  agency_name,vehicle_name,contact_no,rs_per_hour,other_info;
    TextView vehicle_type;
    ImageView imgview2;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    CheckBox cycle,twow,bus,fourw;
    String str1;
    String str2;
    String str3;
    String str4;
    String string;

    String str_aname,str_vname,str_vtype,str_contactno,str_rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_admin);
        storageReference = FirebaseStorage.getInstance().getReference("Member7");
        databaseReference = FirebaseDatabase.getInstance().getReference("Member7");
        btnbrowse = (Button) findViewById(R.id.btnbrowse1);
        save = (Button) findViewById(R.id.save);

        agency_name=findViewById(R.id.agency_name);
        vehicle_name=findViewById(R.id.vehicle_name);
        vehicle_type=findViewById(R.id.vehicle_type);
        contact_no=findViewById(R.id.contact_no);
        rs_per_hour=findViewById(R.id.rs_per_hour);
        other_info=findViewById(R.id.other_info);

        cycle=findViewById(R.id.vtype1);
        twow=findViewById(R.id.vtype2);
        bus=findViewById(R.id.vtype3);
        fourw=findViewById(R.id.vtype4);

        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cycle.isChecked())
                {
                    str1=cycle.getText().toString();
                }
            }
        });

        twow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(twow.isChecked())
                {
                    str2=twow.getText().toString();
                }
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bus.isChecked())
                {
                    str3=bus.getText().toString();
                }
            }
        });

        fourw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fourw.isChecked())
                {
                    str4=fourw.getText().toString();
                }
            }
        });

        imgview2 = (ImageView) findViewById(R.id.imgview2);
        progressDialog =
                new ProgressDialog(VehicleAdmin.this);// context name as per your project name


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

                str_aname = agency_name.getText().toString();
                str_vname = vehicle_name.getText().toString();

                str_contactno = contact_no.getText().toString();
                str_rs = rs_per_hour.getText().toString();
                if (TextUtils.isEmpty(str_aname) && TextUtils.isEmpty( str_vname) && TextUtils.isEmpty(str_vtype)
                        && TextUtils.isEmpty(str_contactno) && TextUtils.isEmpty( str_rs))
                {

                    Toast.makeText(VehicleAdmin.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_aname)) {
                    agency_name.setError("Agency name is Required");
                }
                if (TextUtils.isEmpty(str_vname)) {
                    vehicle_name .setError("Address is Required");
                }


                if (TextUtils.isEmpty(str_contactno)) {
                    contact_no .setError("Contact is Required");

                }
                if (TextUtils.isEmpty(str_rs)) {
                    rs_per_hour .setError("Rs per hour is Required");

                }
                if(Pattern.matches("[a-zA-Z]+",contact_no.getText())) {

                    contact_no.setError("Contact should not contain alphabets");
                }
                String mobilepattern="[0-9]{10}";
                if(contact_no.getText().length()!= 10)
                {
                    contact_no.setError("Contact should be 10 digit");
                }

                if(!cycle.isChecked() && !twow.isChecked() && !bus.isChecked() && !fourw.isChecked())
                {
                    vehicle_type.setError("Please select any vehicle type");
                }

                if (!TextUtils.isEmpty(str_aname) && !TextUtils.isEmpty( str_vname)
                        && !TextUtils.isEmpty(str_contactno) && !TextUtils.isEmpty( str_rs) && !Pattern.matches("[a-zA-Z]+",contact_no.getText())
                        && contact_no.getText().length()==10 ) {
                    if(cycle.isChecked() || twow.isChecked() || bus.isChecked() || fourw.isChecked()) {
                        UploadImage();
                    }
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

                                    //string="Types of Vehicle : "+str1+"  "+str2+"  "+str3+"  "+str4;
                                    if (cycle.isChecked())
                                    {
                                        string=cycle.getText().toString();
                                        if(twow.isChecked())
                                        {
                                            string=string+", "+twow.getText().toString();
                                        }
                                        if(bus.isChecked())
                                        {
                                            string=string+", "+bus.getText().toString();
                                        }
                                        if(fourw.isChecked())
                                        {
                                            string=string+", "+fourw.getText().toString();
                                        }
                                    }

                                    string="Types of vehicles : "+string;
                                    @SuppressWarnings("VisibleForTests")
                                    Member7 imageUploadInfo =
                                            new Member7(agency_name.getText().toString().trim(),vehicle_name.getText().toString().trim(),
                                                    string,
                                                    "Contact No : "+contact_no.getText().toString().trim(),"Rupees per hour : "+rs_per_hour.getText().toString().trim()+"/-"
                                                    ,other_info.getText().toString().trim(),uri.toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                }


                            });

                        }


                    });

        } else {

            Toast.makeText(VehicleAdmin.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}


