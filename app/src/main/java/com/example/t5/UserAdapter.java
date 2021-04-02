package com.example.t5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private static final String TAG="UserAdapter";


    private Context mContext2;
    private ArrayList<UsersPost> imagelist2;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context context, ArrayList<UsersPost> imagelist2)
    {
        this.mContext2=context;
        this.imagelist2=imagelist2;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getUsername());
        holder.t2.setText(imagelist2.get(position).getCaption());
        Picasso.with(mContext2).load(imagelist2.get(position).getPicture()).fit().into(holder.imageAddress1);

    }

    @Override
    public int getItemCount() {
        return imagelist2.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2;
        ImageView imageAddress1;

        public  ViewHolder(View itemView)
        {
            super(itemView);

            t1=(TextView)itemView.findViewById(R.id.user);
            t2=(TextView)itemView.findViewById(R.id.caption);

            imageAddress1=(ImageView) itemView.findViewById(R.id.image2);


        }
    }



}

