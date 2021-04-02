package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Hotel extends AppCompatActivity {

    private static final String TAG="Hotel";

    private DatabaseReference reference;
    private StorageReference mSorageRef;

    private Context mContext2=Hotel.this;
    private RecyclerView recyclerView;

    private ArrayList<Member> imageslist2;
    private MyAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Log.d(TAG,"OnCreate Started");


        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
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

        Query query=reference.child("Member");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    Member member2=new Member();
                    member2.setHotelName(snapshot.child("hotelName").getValue().toString());
                    member2.setCountry(snapshot.child("country").getValue().toString());
                    member2.setLocation(snapshot.child("location").getValue().toString());
                    member2.setCheckout_time(snapshot.child("checkout_time").getValue().toString());
                    member2.setContact(snapshot.child("contact").getValue().toString());
                    member2.setPrice_range(snapshot.child("price_range").getValue().toString());
                    member2.setImageAddress(snapshot.child("imageAddress").getValue().toString());
                    imageslist2.add(member2);
                }
                recyclerAdapter=new MyAdapter(mContext2,imageslist2);
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