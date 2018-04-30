package com.yourscab.driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourscab.driver.R;
import com.yourscab.driver.prototype.PanelRecylerViewPrototype;

import java.util.ArrayList;



public class PanelRecyclerViewAdapter extends RecyclerView.Adapter<PanelRecyclerViewAdapter.MyViewHolder> {

    Context context;
    public ArrayList<PanelRecylerViewPrototype> panelRecylerViewPrototypes;

    public PanelRecyclerViewAdapter(Context context, ArrayList<PanelRecylerViewPrototype> panelRecylerViewPrototypes) {
        this.context = context;
        this.panelRecylerViewPrototypes = panelRecylerViewPrototypes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.panel_item_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.extra_distance_tv.append(panelRecylerViewPrototypes.get(position).getExtra_distance());
        holder.source_item_tv.append(panelRecylerViewPrototypes.get(position).getSource_name());
        holder.destination_item_tv.append(panelRecylerViewPrototypes.get(position).getDestination_name());
        holder.total_kms_tv.append(panelRecylerViewPrototypes.get(position).getTravel_distance());
        holder.price_tv.append(panelRecylerViewPrototypes.get(position).getMoney());
        holder.date_time_tv.append(panelRecylerViewPrototypes.get(position).getDate_travel());
        holder.user_name_details.append(panelRecylerViewPrototypes.get(position).getCustomer_name() + ", " + panelRecylerViewPrototypes.get(position).getCustomer_phone());


    }

    @Override
    public int getItemCount() {
        return panelRecylerViewPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView extra_distance_tv, source_item_tv, destination_item_tv, total_kms_tv, price_tv, date_time_tv, user_name_details;


        public MyViewHolder(View itemView) {
            super(itemView);

            extra_distance_tv = (TextView) itemView.findViewById(R.id.extra_distance);
            source_item_tv = (TextView) itemView.findViewById(R.id.source_item);
            destination_item_tv = (TextView) itemView.findViewById(R.id.destination_item);
            total_kms_tv = (TextView) itemView.findViewById(R.id.total_kms_item);
            price_tv = (TextView) itemView.findViewById(R.id.total_price);
            date_time_tv = (TextView) itemView.findViewById(R.id.date_time_item);
            user_name_details = (TextView) itemView.findViewById(R.id.user_name_details);

        }
    }
}
