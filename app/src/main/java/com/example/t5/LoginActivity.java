package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView txt_signup;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        txt_signup = findViewById(R.id.txt_signup);
        fauth = FirebaseAuth.getInstance();
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("please wait...");
                pd.show();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    email.setError("Field cannot be empty ");
                    password.setError("Field cannot be empty");
                    Toast.makeText(LoginActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    email.setError("Please enter a valid email address ....");
                }
                else if (str_email.equals("admin@gmail.com") && str_password.equals("admin123")) {
                    Intent intent = new Intent(LoginActivity.this, DashboardAdmin.class);
                    startActivity(intent);
                    finish();
                } else {

                    fauth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fauth.getCurrentUser().isEmailVerified()) {

                                    DatabaseReference reference =
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(fauth.getCurrentUser().getUid());
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            pd.dismiss();
                                            Intent intent =
                                                    new Intent(LoginActivity.this, Dashboard.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            pd.dismiss();

                                        }
                                    });
                                } else {

                                    Toast.makeText(LoginActivity.this, "Please verify", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }

        });

    }
}
