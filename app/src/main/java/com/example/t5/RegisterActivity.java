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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password;
    Button register;
    TextView txt_login,txt_profile;
    ImageView userprofilepic;
    FirebaseStorage firebaseStorage;
    FirebaseAuth fauth;
    DatabaseReference reference;
    String str_username, str_fullname, str_email, str_password;
    ProgressDialog pd;
    Uri imagePath;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

                userprofilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_profile=findViewById(R.id.txt_profile);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        userprofilepic = findViewById(R.id.profile_pic);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);
        fullname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        fauth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Pic"), PICK_IMAGE);
            }
        });


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait....");
                pd.show();
                String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                str_username = username.getText().toString();
                str_fullname = fullname.getText().toString();
                str_email = email.getText().toString();
                str_password = password.getText().toString();
                if(imagePath ==null)
                {
                    Toast.makeText(RegisterActivity.this,"Please select profile image",Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(str_username) && TextUtils.isEmpty(str_fullname) && TextUtils.isEmpty(str_email)
                        && TextUtils.isEmpty(str_password) && imagePath == null)
                {
                    // username .setError("Username is Required");
                    // fullname.setError("Fullname is Required");
                    //  password.setError("Password is Required");
                    //  email.setError("Email is Required");
                    // txt_profile.setError("Please select Profile Picture");
                    // userprofilepic.setError("Email is Required");
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(str_username)) {
                    username.setError("Username is Required");
                }
                if (TextUtils.isEmpty(str_fullname)) {
                    fullname .setError("Full name is Required");
                }
                if (TextUtils.isEmpty(str_email)) {
                    email .setError("Email is Required");

                }
                if (TextUtils.isEmpty(str_password)) {
                    password .setError("Password is Required");

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    email.setError("Please enter a valid email address ....");

                }
                if (str_password.length() < 6) {
                    password.setError("Password must be >= 6 Characters");

                }


                if (!TextUtils.isEmpty(str_username) && !TextUtils.isEmpty(str_fullname) && !TextUtils.isEmpty(str_email)
                        && !TextUtils.isEmpty(str_password) && imagePath != null && Patterns.EMAIL_ADDRESS.matcher(str_email).matches()
                        && str_password.length() >= 6)
                {
                    register(str_username,str_fullname,str_email,str_password);

                }
            }



        });
    }

    private void register(final String username, final String fullname, final String email, String password) {

        fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                cleartext();
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registered Successfully. Please Verify", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "You can't register with this email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cleartext() {
        username.setText("");
        email.setText("");
        fullname.setText("");
        password.setText("");
        userprofilepic.setImageResource(R.drawable.ic_profile);
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference(fauth.getUid());
        StorageReference imageReference =
                storageReference.child(fauth.getUid()).child("Images").child("ProfilePic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Upload FailedU", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegisterActivity.this, "Thank You For Registration", Toast.LENGTH_SHORT).show();
            }
        });
        String str_add=null, str_phone = null;
        User user = new User(str_email, str_fullname, str_username,str_phone,str_add);
        reference.setValue(user);

    }
    public static final Pattern EMAIL_ADDRESS
            =Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
   /* public static final Pattern password_pattner
            =Pattern.compile("^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                 ".{6,}" +
            "$"); */

}
