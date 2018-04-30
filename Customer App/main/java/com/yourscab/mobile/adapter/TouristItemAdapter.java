package com.yourscab.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yourscab.mobile.R;
import com.yourscab.mobile.prototype.TouristItemPrototype;
import com.yourscab.mobile.subactivities.TouristPackageDetails;

import java.util.ArrayList;



public class TouristItemAdapter extends RecyclerView.Adapter<TouristItemAdapter.MyViewHolder> {

    Context context;
    public ArrayList<TouristItemPrototype> touristItemPrototypes;

    public TouristItemAdapter(Context context, ArrayList<TouristItemPrototype> touristItemPrototypes) {
        this.context = context;
        this.touristItemPrototypes = touristItemPrototypes;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tourist_item_horizontal_recyclerview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // GlideApp.with(context).load(Uri.parse(touristItemPrototypes.get(position).getImage_url()));

        if (touristItemPrototypes.get(position).getImage_url() != null) {
            Glide.with(context).load(Uri.parse(touristItemPrototypes.get(position).getImage_url())).thumbnail(0.2f).into(holder.imageView);
        }

        holder.title.setText(touristItemPrototypes.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the detilas of the package for the it
                Intent intent = new Intent(context, TouristPackageDetails.class);
                intent.putExtra("title",touristItemPrototypes.get(position).getTitle());
                intent.putExtra("node",touristItemPrototypes.get(position).getNode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return touristItemPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.touristitem_textview_title);
            imageView = (ImageView) itemView.findViewById(R.id.touristitem_imageview);

        }
    }
}
