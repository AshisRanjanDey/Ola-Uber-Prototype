package com.yourscab.mobile.data;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.adapter.TouristItemAdapter;
import com.yourscab.mobile.prototype.TouristItemPrototype;

import java.util.ArrayList;


public class TouristItemData {

    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public ArrayList<TouristItemPrototype> touristItemPrototypes = new ArrayList<>();

    public TouristItemData(Context context, RecyclerView recyclerView, final ProgressBar progressBar, String parent_node) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;

        FirebaseDatabase.getInstance().getReference().child("tourist").child(parent_node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                touristItemPrototypes.clear();
                getUpdates(dataSnapshot);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String node = ds.getValue().toString().trim();

            loadData(node);

        }


    }

    public void loadData(final String node) {
        FirebaseDatabase.getInstance().getReference().child("package").child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("package_name").getValue().toString().trim();
                String image_url = dataSnapshot.child("image_url").getValue().toString().trim();

                touristItemPrototypes.add(new TouristItemPrototype(image_url, name, node));

                if (touristItemPrototypes.size() > 0) {
                    TouristItemAdapter touristItemAdapter = new TouristItemAdapter(context, touristItemPrototypes);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(touristItemAdapter);

                } else {
                    // no data is there handing

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
