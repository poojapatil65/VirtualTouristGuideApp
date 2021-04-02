package com.example.t5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private static final String TAG="MyAdapter";


    private Context mContext;
    private ArrayList<Member> imagelist2;

    public MyAdapter (Context context, ArrayList<Member> imagelist2)
    {
        this.mContext=context;
        this.imagelist2=imagelist2;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getHotelName());
        holder.t2.setText(imagelist2.get(position).getCountry());
        holder.t3.setText(imagelist2.get(position).getLocation());
        holder.t4.setText(imagelist2.get(position).getCheckout_time());
        holder.t5.setText(imagelist2.get(position).getPrice_range());
        holder.t6.setText(imagelist2.get(position).getContact());
        Picasso.with(mContext).load(imagelist2.get(position).getImageAddress()).fit().into(holder.imageAddress1);
    }

    @Override
    public int getItemCount() {
        return imagelist2.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5,t6;
        ImageView imageAddress1;

        public  ViewHolder(View itemView)
        {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.hotelName);
            t2=(TextView)itemView.findViewById(R.id.country);
            t3=(TextView)itemView.findViewById(R.id.location);
            t4=(TextView)itemView.findViewById(R.id.checkout_time);
            t5=(TextView)itemView.findViewById(R.id.price_range);
            t6=(TextView)itemView.findViewById(R.id.contact);
            imageAddress1=(ImageView) itemView.findViewById(R.id.image2);



        }
    }


}
