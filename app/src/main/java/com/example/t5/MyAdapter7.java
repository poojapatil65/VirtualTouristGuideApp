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

public class MyAdapter7 extends RecyclerView.Adapter<MyAdapter7.ViewHolder>
{
    private static final String TAG="MyAdapter7";


    private Context mContext2;
    private ArrayList<Member7> imagelist2;

    public MyAdapter7(Context context, ArrayList<Member7> imagelist2)
    {
        this.mContext2=context;
        this.imagelist2=imagelist2;
    }

    @NonNull
    @Override
    public MyAdapter7.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle1,parent,false);
        return new MyAdapter7.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter7.ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getAgency_name());
        holder.t2.setText(imagelist2.get(position).getVehicle_name());
        holder.t3.setText(imagelist2.get(position).getVehicle_type());
        holder.t4.setText(imagelist2.get(position).getContact_no());
        holder.t5.setText(imagelist2.get(position).getRs_per_hour());
        holder.t6.setText(imagelist2.get(position).getOther_info());

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
            t1=(TextView)itemView.findViewById(R.id.agency_name);
            t2=(TextView)itemView.findViewById(R.id.vehicle_name);
            t3=(TextView)itemView.findViewById(R.id.vehicle_type);
            t4=(TextView)itemView.findViewById(R.id.contact_no);
            t5=(TextView)itemView.findViewById(R.id.rs_per_hour);
            t6=(TextView)itemView.findViewById(R.id.other_info);
            imageAddress=(ImageView) itemView.findViewById(R.id.image2);


        }
    }

}