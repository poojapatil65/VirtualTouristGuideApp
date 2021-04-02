package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Beach extends AppCompatActivity {

    private static final String TAG="Beach";

    private DatabaseReference reference;
    private StorageReference mSorageRef;

    private Context mContext2=Beach.this;
    private RecyclerView recyclerView;

    private ArrayList<Member2> imageslist2;
    private MyAdapter2 recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach);
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

        Query query=reference.child("Member2");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Member2 member2=new Member2();
                    member2.setMbeachName(snapshot.child("mbeachName").getValue().toString());
                    member2.setMcountry(snapshot.child("mcountry").getValue().toString());
                    member2.setMstate(snapshot.child("mstate").getValue().toString());
                    member2.setMideal_trip_duration(snapshot.child("mideal_trip_duration").getValue().toString());
                    member2.setMtime_to_visit(snapshot.child("mtime_to_visit").getValue().toString());
                    member2.setMinfo(snapshot.child("minfo").getValue().toString());
                    member2.setMimageAddress2(snapshot.child("mimageAddress2").getValue().toString());
                    imageslist2.add(member2);
                }
                recyclerAdapter=new MyAdapter2(mContext2,imageslist2);
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

