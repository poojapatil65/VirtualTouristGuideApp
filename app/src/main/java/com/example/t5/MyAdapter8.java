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

public class MyAdapter8 extends RecyclerView.Adapter<MyAdapter8.ViewHolder>
{
    private static final String TAG="MyAdapter8";


    private Context mContext2;
    private ArrayList<Member1> imagelist2;

    public MyAdapter8(Context context, ArrayList<Member1> imagelist2)
    {
        this.mContext2=context;
        this.imagelist2=imagelist2;
    }

    @NonNull
    @Override
    public MyAdapter8.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital1,parent,false);
        return new MyAdapter8.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter8.ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getHospital_name());
        holder.t2.setText(imagelist2.get(position).getCountry());
        holder.t3.setText(imagelist2.get(position).getLocation());
        holder.t4.setText(imagelist2.get(position).getDegree());
        holder.t5.setText(imagelist2.get(position).getContact_no());
        holder.t6.setText(imagelist2.get(position).getTime_duration());

        Picasso.with(mContext2).load(imagelist2.get(position).getImageAddress()).fit().into(holder.imageAddress);
    }

    @Override
    public int getItemCount() {
        return imagelist2.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5,t6;
        ImageView imageAddress;

        public  ViewHolder(View itemView)
        {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.hospital_name);
            t2=(TextView)itemView.findViewById(R.id.country1);
            t3=(TextView)itemView.findViewById(R.id.location1);
            t4=(TextView)itemView.findViewById(R.id.degree);
            t5=(TextView)itemView.findViewById(R.id.contact_no);
            t6=(TextView)itemView.findViewById(R.id.time_duration);
            imageAddress=(ImageView) itemView.findViewById(R.id.image2);


        }
    }

}