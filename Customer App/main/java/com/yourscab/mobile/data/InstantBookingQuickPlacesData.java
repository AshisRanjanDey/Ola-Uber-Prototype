package com.yourscab.mobile.data;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.adapter.InstantBookingQuickPlacesAdapter;
import com.yourscab.mobile.prototype.InstantBookingQuickPlacesPrototype;

import java.util.ArrayList;


public class InstantBookingQuickPlacesData {
    Context context;
    public RecyclerView recyclerView;
    public ArrayList<InstantBookingQuickPlacesPrototype> instantBookingQuickPlacesPrototypes = new ArrayList<>();
    public ProgressBar progressBar;

    public InstantBookingQuickPlacesData(Context context, RecyclerView recyclerView, final ProgressBar progressBar) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;

        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().child("recent_places").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                getUpdates(dataSnapshot);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void getUpdates(final DataSnapshot dataSnap) {
        String njp_img = "http://siliguritimes.com/wp-content/uploads/2017/04/61462977.jpg";
        String bag_img = "http://siliguritimes.com/wp-content/uploads/2017/07/05NBLttairport_195002.jpg";

        // first add two destination like njp and bagadogra
        InstantBookingQuickPlacesPrototype njp = new InstantBookingQuickPlacesPrototype("" + njp_img, "NJP junction", 26.6842, 88.4428);
        InstantBookingQuickPlacesPrototype bagadogra = new InstantBookingQuickPlacesPrototype(bag_img, "Bagadogra Airport", 26.6994, 88.3143);

        instantBookingQuickPlacesPrototypes.clear();
        // instantBookingQuickPlacesPrototypes.add(njp);
        //instantBookingQuickPlacesPrototypes.add(bagadogra);


        for (DataSnapshot ds : dataSnap.getChildren()) {

            loadData(ds);
        }

        if (instantBookingQuickPlacesPrototypes.size() > 0) {
            InstantBookingQuickPlacesAdapter instantBookingQuickPlacesAdapter = new InstantBookingQuickPlacesAdapter(context, instantBookingQuickPlacesPrototypes);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(instantBookingQuickPlacesAdapter);

        } else {
            Toast.makeText(context, "No quick spots found for this destination...", Toast.LENGTH_SHORT).show();
        }

    }

    public void loadData(DataSnapshot dataSnapshot1) {

        try {

            double lat1 = (double) dataSnapshot1.child("lat").getValue();
            double lon1 = (double) dataSnapshot1.child("lon").getValue();


            String name1 = dataSnapshot1.child("name").getValue().toString().trim();
            String image_url1 = dataSnapshot1.child("image_url").getValue().toString().trim();

            InstantBookingQuickPlacesPrototype instantBookingQuickPlacesPrototype = new InstantBookingQuickPlacesPrototype(image_url1, name1, lat1, lon1);
            instantBookingQuickPlacesPrototypes.add(instantBookingQuickPlacesPrototype);

        } catch (Exception e) {


        }


    }
}