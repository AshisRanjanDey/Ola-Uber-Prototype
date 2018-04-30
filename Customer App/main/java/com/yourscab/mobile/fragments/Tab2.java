package com.yourscab.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yourscab.mobile.R;
import com.yourscab.mobile.data.TouristItemData;


public class Tab2 extends Fragment {

    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;

    public Tab2() {
    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
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

        View view = inflater.inflate(R.layout.tab2,container,false);


            recyclerView1 = (RecyclerView) view.findViewById(R.id.horizonstal_recyclerview_1);
            recyclerView2 = (RecyclerView) view.findViewById(R.id.horizonstal_recyclerview_2);
            recyclerView3 = (RecyclerView) view.findViewById(R.id.horizonstal_recyclerview_3);
            recyclerView4 = (RecyclerView) view.findViewById(R.id.horizonstal_recyclerview_4);

            progressBar1 = (ProgressBar) view.findViewById(R.id.progressbar1);
            progressBar2 = (ProgressBar) view.findViewById(R.id.progressbar2);
            progressBar3 = (ProgressBar) view.findViewById(R.id.progressbar3);
            progressBar4 = (ProgressBar) view.findViewById(R.id.progressbar4);


        try {
            TouristItemData touristItemData = new TouristItemData(getContext(), recyclerView1, progressBar1, "east_sikkim");
            TouristItemData touristItemData2 = new TouristItemData(getContext(), recyclerView2, progressBar2, "west_sikkim");
            TouristItemData touristItemData3 = new TouristItemData(getContext(), recyclerView3, progressBar3, "north_sikkim");
            TouristItemData touristItemData4 = new TouristItemData(getContext(), recyclerView4, progressBar4, "south_sikkim");


        } catch (Exception e) {
            Toast.makeText(getContext(), "Error" + e, Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}



