package com.yourscab.mobile.subFragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourscab.mobile.R;
import com.yourscab.mobile.fragments.Tab3;


public class PackageBookingSubFragment extends Fragment {

    public PackageBookingSubFragment(){}
    public static PackageBookingSubFragment newInstance(String param1, String param2) {
        PackageBookingSubFragment fragment = new PackageBookingSubFragment();
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
        View view=  inflater.inflate(R.layout.package_booking_subfragment, container, false);





        return view;
    }

}
