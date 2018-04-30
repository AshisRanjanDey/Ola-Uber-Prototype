package com.yourscab.mobile.other;

import java.text.SimpleDateFormat;
import java.util.Date;



public class OtherMethods {

    public String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}
