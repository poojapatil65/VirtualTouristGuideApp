package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;

public class ChangePassword extends AppCompatActivity {

    EditText newpass;
    Button updatepass;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        updatepass = findViewById(R.id.updatepass);
        newpass = findViewById(R.id.newpass);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userpassword = newpass.getText().toString();
                if (! validatePassword()) {
                    return;
                }
                else
                    {
                    firebaseUser.updatePassword(userpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ChangePassword.this, "Password Changed Failed", Toast.LENGTH_LONG).show();
                                finish();
                            }

                        }
                    });
                }
            }

            private boolean validatePassword() {
                String spassword = newpass.getText().toString();
                String passwordpattern = "^" +
                        //"(?=.*[0-9])"+
                        //"(?=.*[a-z])"+
                        //"(?=.*[A-Z])"+
                        "(?=.*[a-zA-Z])" +
                        "(?=.*[@#$%^&+=])" +
                        "(?=\\S+$)" +
                        ".{4,}" +
                        "$";

                if (spassword.isEmpty())
                {
                    newpass.setError("Field cannot be empty");
                    return false;
                } else
                if (!spassword.matches(passwordpattern))
                {
                    newpass.setError("Password is too weak");
                    return false;
                }
                else
                {
                    newpass.setError(null);
                    return true;
                }
            }
        });

    }
}
