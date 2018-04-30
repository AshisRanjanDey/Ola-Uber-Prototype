package com.yourscab.driver.data;

import android.content.Context;

import com.yourscab.driver.prototype.NavPrototype;

import java.util.ArrayList;



public class NavData {

    public static ArrayList<NavPrototype> navPrototypes = new ArrayList<>();

    public static void loadData(Context context){
        navPrototypes.clear();
        navPrototypes.add(new NavPrototype("Home"));
        navPrototypes.add(new NavPrototype("Accepted Orders"));
        navPrototypes.add(new NavPrototype("Settings"));
        navPrototypes.add(new NavPrototype("Home"));


    }
}
