package com.example.t5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.ViewHolder>
{
    private static final String TAG="MyAdapter5";


    private Context mContext2;
    private ArrayList<Member4> imagelist2;

    public MyAdapter5(Context context, ArrayList<Member4> imagelist2)
    {
        this.mContext2=context;
        this.imagelist2=imagelist2;
    }

    @NonNull
    @Override
    public MyAdapter5.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fort1,parent,false);
        return new MyAdapter5.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter5.ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getFortName());
        holder.t2.setText(imagelist2.get(position).getCountry());
        holder.t3.setText(imagelist2.get(position).getLocation());
        holder.t4.setText(imagelist2.get(position).getTiming());
        holder.t5.setText(imagelist2.get(position).getFort_info());

        Picasso.with(mContext2).load(imagelist2.get(position).getImageAddress()).fit().into(holder.imageAddress);
    }

    @Override
    public int getItemCount() {
        return imagelist2.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5;
        ImageView imageAddress;

        public  ViewHolder(View itemView)
        {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.fortName);
            t2=(TextView)itemView.findViewById(R.id.country);
            t3=(TextView)itemView.findViewById(R.id.location);
            t4=(TextView)itemView.findViewById(R.id.timing);
            t5=(TextView)itemView.findViewById(R.id.fort_info);
            imageAddress=(ImageView) itemView.findViewById(R.id.image2);


        }
    }

}

