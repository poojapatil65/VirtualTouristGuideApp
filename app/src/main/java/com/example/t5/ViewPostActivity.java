package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ViewPostActivity extends AppCompatActivity {

    private static final String TAG="ViewPostActivity";

    private DatabaseReference reference;
    private StorageReference mSorageRef;

    private Context mContext2=ViewPostActivity.this;
    private RecyclerView recyclerView;

    private ArrayList<UsersPost> imageslist2;
    private UserAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Log.d(TAG,"OnCreate Started");

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        reference= FirebaseDatabase.getInstance().getReference();
        mSorageRef= FirebaseStorage.getInstance().getReference();

        imageslist2=new ArrayList<>();
        init();


    }
    private void  init()
    {

        clearAll();

        Query query=reference.child("UsersPost");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                 UsersPost member2=new UsersPost();
                    member2.setPicture(snapshot.child("picture").getValue().toString());
                    member2.setCaption(snapshot.child("caption").getValue().toString());
                    member2.setUsername(snapshot.child("username").getValue().toString());

                    imageslist2.add(member2);
                }
                recyclerAdapter=new UserAdapter(mContext2,imageslist2);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clearAll()
    {
        if(imageslist2!=null)
        {
            imageslist2.clear();
            if(recyclerAdapter != null)
            {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        imageslist2=new ArrayList<>();
    }
}

