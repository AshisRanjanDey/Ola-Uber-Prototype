package com.yourscab.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yourscab.driver.R;
import com.yourscab.driver.data.NavData;

import static com.yourscab.driver.data.NavData.navPrototypes;



public class NavAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater layoutInflater;

    public NavAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        NavData.loadData(context);

    }

    @Override
    public int getCount() {
        return navPrototypes.size();
    }

    @Override
    public Object getItem(int i) {
        return navPrototypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.nav_layout_linearlayout, viewGroup, false);
        TextView title = (TextView) linearLayout.findViewById(R.id.tv_title_navlayout);

        title.setText(navPrototypes.get(i).getTitle());


        return linearLayout;
    }
}
