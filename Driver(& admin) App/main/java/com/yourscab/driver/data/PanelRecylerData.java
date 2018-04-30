package com.yourscab.driver.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yourscab.driver.adapter.PanelRecyclerViewAdapter;
import com.yourscab.driver.prototype.PanelRecylerViewPrototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class PanelRecylerData {
    Context context;
    ProgressBar progressBar;
    public PanelRecyclerViewAdapter panelRecyclerViewAdapter;
    public static ArrayList<PanelRecylerViewPrototype> panelRecylerViewPrototypes = new ArrayList<>();
    public RecyclerView recyclerView;

    public boolean isDriverAssigned;

    public boolean isDriverAssigned() {
        return isDriverAssigned;
    }

    public void setDriverAssigned(boolean driverAssigned) {
        isDriverAssigned = driverAssigned;
    }

    public PanelRecylerData(final Context context, final ProgressBar progressBar, RecyclerView recyclerView, String driver_id) {

        this.context = context;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        panelRecylerViewPrototypes.clear();
        panelRecyclerViewAdapter = new PanelRecyclerViewAdapter(context, panelRecylerViewPrototypes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(panelRecyclerViewAdapter);
        MyTask myTask = new MyTask();
        myTask.execute(driver_id,null,null);



    }

    public void getUpdates(DataSnapshot dataSnapshot) {
        panelRecylerViewPrototypes.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String is_Confirmed = (String) ds.child("is_confirmed").getValue();
            if (is_Confirmed.equals("no")) {
                // now add it in the prototypes
                String order_id = ds.getKey();

                if (!driver_Assigned(order_id)) {
                    String customer_name = (String) ds.child("c_name").getValue();
                    String customer_phone = (String) ds.child("c_phone").getValue();
                    String customer_token = (String) ds.child("c_token").getValue();
                    String customer_uid = (String) ds.child("c_uid").getValue();
                    String date_travel = (String) ds.child("date_travel").getValue();
                    String money = (String) ds.child("money").getValue();
                    String destination_name = (String) ds.child("destination_name").getValue();
                    String source_name = (String) ds.child("source_name").getValue();
                    String travel_distance = (String) ds.child("travel_distance").getValue();
                    String driver_token = FirebaseInstanceId.getInstance().getToken();
                    String extra_distance = (String) ds.child("extra_distance").getValue();
                    String time_travel = (String) ds.child("time_travel").getValue();
                    double source_lat = (double) ds.child("src_lat").getValue();
                    double source_lon = (double) ds.child("src_lon").getValue();
                    double destination_lat = (double) ds.child("destination_lat").getValue();
                    double destination_lon = (double) ds.child("destination_lon").getValue();

                    PanelRecylerViewPrototype panelRecylerViewPrototype = new PanelRecylerViewPrototype(customer_name,
                            customer_phone, customer_uid, date_travel + ", " + time_travel, destination_lat, destination_lon, source_lat, source_lon,
                            destination_name, source_name, travel_distance, extra_distance, money, is_Confirmed);

                    panelRecylerViewPrototypes.add(panelRecylerViewPrototype);

                }


            }
        }

        if (panelRecylerViewPrototypes.size() > 0) {
            panelRecyclerViewAdapter.notifyDataSetChanged();


        } else {
            Toast.makeText(context, "No orders available at the moment..stay tuned...", Toast.LENGTH_LONG).show();
        }

    }

    public boolean driver_Assigned(String order_id) {
        final boolean bool = false;
        FirebaseDatabase.getInstance().getReference().child("instant_booking_orders").child(order_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String driver_assigned = (String) dataSnapshot.child("driverAssigned").getValue();
                if (driver_assigned.equals("no")) {
                    setDriverAssigned(false);
                } else {
                    setDriverAssigned(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return isDriverAssigned();
    }


    private  class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String driver_id = strings[0];

            FirebaseDatabase.getInstance().getReference().child("driver").child(driver_id).child("order_list").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    panelRecylerViewPrototypes.clear();
                    getUpdates(dataSnapshot);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, "Check your internet connnection...", Toast.LENGTH_SHORT).show();
                }
            });


            return null;
        }
    }
}
