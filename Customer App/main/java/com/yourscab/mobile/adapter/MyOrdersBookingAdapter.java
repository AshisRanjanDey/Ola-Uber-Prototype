package com.yourscab.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yourscab.mobile.R;
import com.yourscab.mobile.prototype.MyOrdersBookingPrototype;

import java.util.ArrayList;



public class MyOrdersBookingAdapter extends RecyclerView.Adapter<MyOrdersBookingAdapter.MyViewHolder> {

    Context context;
    public ArrayList<MyOrdersBookingPrototype> myOrdersBookingPrototypes;

    public MyOrdersBookingAdapter(Context context, ArrayList<MyOrdersBookingPrototype> myOrdersBookingPrototypes) {
        this.context = context;
        this.myOrdersBookingPrototypes = myOrdersBookingPrototypes;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_general, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (myOrdersBookingPrototypes.get(position).getDestination().length() > 40) {
            holder.textView_destination.setText(myOrdersBookingPrototypes.get(position).getDestination().substring(0, 40) + "...");

        } else {
            holder.textView_destination.setText(myOrdersBookingPrototypes.get(position).getDestination());

        }
        if (myOrdersBookingPrototypes.get(position).getSource().length() > 40) {
            holder.textView_src.setText(myOrdersBookingPrototypes.get(position).getSource().substring(0, 40) + "...");

        } else {
            holder.textView_src.setText(myOrdersBookingPrototypes.get(position).getSource());

        }

        holder.ticket_id.setText(myOrdersBookingPrototypes.get(position).getTicket_id());
        holder.journey_time.setText(myOrdersBookingPrototypes.get(position).getDate_travel() + " - " + myOrdersBookingPrototypes.get(position).getTime_travel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrdersBookingPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_src, textView_destination;
        TextView journey_time;
        TextView ticket_id;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_src = (TextView) itemView.findViewById(R.id.order_src);
            textView_destination = (TextView) itemView.findViewById(R.id.order_destination);
            journey_time = (TextView) itemView.findViewById(R.id.order_time);
            ticket_id = (TextView) itemView.findViewById(R.id.order_idno);

        }
    }
}
