package com.yourscab.mobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yourscab.mobile.R;
import com.yourscab.mobile.prototype.InstantBookingQuickPlacesPrototype;

import java.util.ArrayList;


public class InstantBookingQuickPlacesAdapter extends RecyclerView.Adapter<InstantBookingQuickPlacesAdapter.MyViewHolder> {

    Context context;
    public ArrayList<InstantBookingQuickPlacesPrototype> instantBookingQuickPlacesPrototypes;

    public InstantBookingQuickPlacesAdapter(Context context, ArrayList<InstantBookingQuickPlacesPrototype> instantBookingQuickPlacesPrototypes) {
        this.context = context;
        this.instantBookingQuickPlacesPrototypes = instantBookingQuickPlacesPrototypes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tourist_item_horizontal_recyclerview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(instantBookingQuickPlacesPrototypes.get(position).getName());
        if (instantBookingQuickPlacesPrototypes.get(position).getImage_url() != null) {
            Glide.with(context).load(Uri.parse(instantBookingQuickPlacesPrototypes.get(position).getImage_url())).thumbnail(0.2f).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here get the all details and set the value it in the destination view


            }
        });


    }

    @Override
    public int getItemCount() {
        return instantBookingQuickPlacesPrototypes.size();
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
