package com.yourscab.mobile.subFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.R;
import com.yourscab.mobile.data.MyOrdersBookingGeneralData;

import java.util.ArrayList;
import java.util.List;



public class GeneralBookingSubFragment extends Fragment {

    RecyclerView recyclerView;
    public List<String> instant_orderid;
    ProgressBar progressBar;

    public List<String> getInstant_orderid() {
        return instant_orderid;
    }

    public void setInstant_orderid(List<String> instant_orderid) {
        this.instant_orderid = instant_orderid;
    }

    public GeneralBookingSubFragment() {
    }

    public static GeneralBookingSubFragment newInstance(String param1, String param2) {
        GeneralBookingSubFragment fragment = new GeneralBookingSubFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.general_booking_sub_fragment, container, false);


        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        FirebaseDatabase.getInstance().getReference().child("user_orders").child("" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();
                        list.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue().toString().equals("instant")) {

                                String key = ds.getKey();
                                list.add(key);

                            }
                        }
                        setInstant_orderid(list);

                        try {


                            Toast.makeText(getContext(), "" + getInstant_orderid().size(), Toast.LENGTH_LONG).show();
                                        MyOrdersBookingGeneralData myOrdersBookingGeneralData = new MyOrdersBookingGeneralData(getContext(), recyclerView, progressBar, getInstant_orderid());

                        } catch (Exception e) {
                            // catch the exception here
                            Toast.makeText(getContext(), "99" + e, Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Unable to load pas orders...try again..", Toast.LENGTH_LONG).show();
                    }
                });


        return view;
    }

}
