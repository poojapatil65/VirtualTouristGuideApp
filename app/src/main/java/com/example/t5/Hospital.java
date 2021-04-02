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

public class Hospital extends AppCompatActivity {


    private static final String TAG="Hospital";

    private DatabaseReference reference;
    private StorageReference mSorageRef;

    private Context mContext2=Hospital.this;
    private RecyclerView recyclerView;

    private ArrayList<Member1> imageslist2;
    private MyAdapter8 recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

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

        Query query=reference.child("Member1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Member1 member2=new Member1();
                    member2.setHospital_name(snapshot.child("hospital_name").getValue().toString());
                    member2.setCountry(snapshot.child("country").getValue().toString());
                    member2.setLocation(snapshot.child("location").getValue().toString());
                    member2.setDegree(snapshot.child("degree").getValue().toString());
                    member2.setContact_no(snapshot.child("contact_no").getValue().toString());
                    member2.setTime_duration(snapshot.child("time_duration").getValue().toString());
                    member2.setImageAddress(snapshot.child("imageAddress").getValue().toString());
                    imageslist2.add(member2);
                }
                recyclerAdapter=new MyAdapter8(mContext2,imageslist2);
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