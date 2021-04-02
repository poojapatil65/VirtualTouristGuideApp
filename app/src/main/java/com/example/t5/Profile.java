package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class Profile extends AppCompatActivity {

    private TextView e1, e2, e3,e4,e5;
    private ImageView i1;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        e1 = findViewById(R.id.username);
        e2 = findViewById(R.id.fullname);
        e3 = findViewById(R.id.bio);
        e4=findViewById(R.id.phn_no);
        e5=findViewById(R.id.add);
        i1 = findViewById(R.id.imageView2);

        progressDialog =
                new ProgressDialog(Profile.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        reference = database.getReference(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic").getDownloadUrl().addOnSuccessListener
                (new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        // context=firebaseAuth.getCurrentUser().getUid();
                        Picasso.with(getBaseContext()).load(uri).fit().into(i1);
                    }
                });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User user = dataSnapshot.getValue(User.class);

                e1.setText(user.getUsername());
                e2.setText(user.getFullname());
                e3.setText(user.getEmail());
                e4.setText(user.getPhone_no());
                e5.setText(user.getAddress());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(), Post.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.profile:

                        return true;


                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.updateprofile) {
            intent = new Intent(Profile.this, UpdateProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.changepass) {
            intent = new Intent(Profile.this, ChangePassword.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            intent = new Intent(Profile.this, Main3Activity.class);
            startActivity(intent);
            finish();
            Toast.makeText(Profile.this, "Successfully Logout", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



