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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>
        {
private static final String TAG="MyAdapter2";


private Context mContext2;
private ArrayList<Member2> imagelist2;

public MyAdapter2(Context context, ArrayList<Member2> imagelist2)
        {
        this.mContext2=context;
        this.imagelist2=imagelist2;
        }

            @NonNull
            @Override
            public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.beach1,parent,false);
                return new MyAdapter2.ViewHolder(view);
            }

            @Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.t1.setText(imagelist2.get(position).getMbeachName());
        holder.t2.setText(imagelist2.get(position).getMcountry());
        holder.t3.setText(imagelist2.get(position).getMstate());
        holder.t4.setText(imagelist2.get(position).getMideal_trip_duration());
        holder.t5.setText(imagelist2.get(position).getMtime_to_visit());
        holder.t6.setText(imagelist2.get(position).getMinfo());

        Picasso.with(mContext2).load(imagelist2.get(position).getMimageAddress2()).fit().into(holder.imageAddress1);
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

        t1=(TextView)itemView.findViewById(R.id.beachName);
        t2=(TextView)itemView.findViewById(R.id.country);
        t3=(TextView)itemView.findViewById(R.id.state);
        t4=(TextView)itemView.findViewById(R.id.xtrip_duration);
        t5=(TextView)itemView.findViewById(R.id.xtime_to_visit);
        t6=(TextView)itemView.findViewById(R.id.info);
        imageAddress1=(ImageView) itemView.findViewById(R.id.image2);


    }
}

}
