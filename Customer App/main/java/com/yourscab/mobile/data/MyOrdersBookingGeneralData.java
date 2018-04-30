package com.yourscab.mobile.data;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.adapter.MyOrdersBookingAdapter;
import com.yourscab.mobile.prototype.MyOrdersBookingPrototype;

import java.util.ArrayList;
import java.util.List;



public class MyOrdersBookingGeneralData {
    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<String> tickets_id;
    public static ArrayList<MyOrdersBookingPrototype> myOrdersBookingPrototypes = new ArrayList<>();

    public MyOrdersBookingGeneralData(final Context context, RecyclerView recyclerView, final ProgressBar progressBar, final List<String> tickets_id) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.tickets_id = tickets_id;

        FirebaseDatabase.getInstance().getReference().child("instant_booking_orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myOrdersBookingPrototypes.clear();
                getUpdates(dataSnapshot);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Unable to load data...please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getUpdates(DataSnapshot ds) {

        for (int i = tickets_id.size() - 1; i >= 0; i--) {
            DataSnapshot dataSnapshot = ds.child("" + tickets_id.get(i));

            String date = (String) dataSnapshot.child("date").getValue();
            String destination = (String) dataSnapshot.child("destination").getValue();
            String distance = (String) dataSnapshot.child("distance").getValue();
            String order_data = (String) dataSnapshot.child("order_date").getValue();
            String phone_number = (String) dataSnapshot.child("phone_number").getValue();
            String price = (String) dataSnapshot.child("price").getValue();
            String source = (String) dataSnapshot.child("source").getValue();
            String ticketId = (String) dataSnapshot.child("ticket_id").getValue();
            String time = (String) dataSnapshot.child("time").getValue();
            String user_name = (String) dataSnapshot.child("user_name").getValue();

            double destination_lat = (double) dataSnapshot.child("destination_lat").getValue();
            double destination_lon = (double) dataSnapshot.child("destination_lon").getValue();
            double src_lat = (double) dataSnapshot.child("src_lat").getValue();
            double src_lon = (double) dataSnapshot.child("src_lon").getValue();


            MyOrdersBookingPrototype myOrdersBookingPrototype = new MyOrdersBookingPrototype();
            myOrdersBookingPrototype.setDate_booking(order_data);
            myOrdersBookingPrototype.setDate_travel(date);
            myOrdersBookingPrototype.setDestination(destination);
            myOrdersBookingPrototype.setDestination_lat(destination_lat);
            myOrdersBookingPrototype.setDestination_lon(destination_lon);
            myOrdersBookingPrototype.setDistance(distance);
            myOrdersBookingPrototype.setPhone(phone_number);
            myOrdersBookingPrototype.setSource(source);
            myOrdersBookingPrototype.setSrc_lat(src_lat);
            myOrdersBookingPrototype.setSrc_lon(src_lon);
            myOrdersBookingPrototype.setTicket_id(ticketId);
            myOrdersBookingPrototype.setTime_travel(time);
            myOrdersBookingPrototype.setUser_name(user_name);
            myOrdersBookingPrototype.setPrice(price);

            //  MyOrdersBookingPrototype myOrdersBookingPrototype1 =new MyOrdersBookingPrototype(destination,source,date,order_data,ticketId);
            myOrdersBookingPrototypes.add(myOrdersBookingPrototype);

        }

        if (myOrdersBookingPrototypes.size() > 0) {
            MyOrdersBookingAdapter myOrdersBookingAdapter = new MyOrdersBookingAdapter(context, myOrdersBookingPrototypes);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(myOrdersBookingAdapter);

        } else {
            // it is empty or no orders found in the database

        }

    }
}
