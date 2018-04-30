package com.yourscab.mobile.prototype;



public class TicketGeneralDriverKeLiyePrototype {
    public String c_name, c_phone, c_token, c_uid, travel_distance, destination_name;
    public double src_lat, src_lon, destination_lat, destination_lon;
    public String money, extra_distance, date_travel, source_name, time_travel;
    public String driver_token, driver_id;
    public String is_confirmed;


    public TicketGeneralDriverKeLiyePrototype(String c_name, String c_phone, String c_token, String c_uid,
                                              String travel_distance, String destination_name, double src_lat,
                                              double src_lon, double destination_lat,
                                              double destination_lon, String money, String extra_distance,
                                              String date_travel, String source_name, String driver_token,
                                              String driver_id, String is_confirmed, String time_travel) {
        this.c_name = c_name;
        this.c_phone = c_phone;
        this.c_token = c_token;
        this.c_uid = c_uid;
        this.travel_distance = travel_distance;
        this.destination_name = destination_name;
        this.src_lat = src_lat;
        this.src_lon = src_lon;
        this.destination_lat = destination_lat;
        this.destination_lon = destination_lon;
        this.money = money;
        this.extra_distance = extra_distance;
        this.date_travel = date_travel;
        this.source_name = source_name;
        this.driver_token = driver_token;
        this.driver_id = driver_id;
        this.is_confirmed = is_confirmed;
        this.time_travel = time_travel;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_token() {
        return c_token;
    }

    public void setC_token(String c_token) {
        this.c_token = c_token;
    }

    public String getC_uid() {
        return c_uid;
    }

    public void setC_uid(String c_uid) {
        this.c_uid = c_uid;
    }

    public String getTravel_distance() {
        return travel_distance;
    }

    public void setTravel_distance(String travel_distance) {
        this.travel_distance = travel_distance;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public double getSrc_lat() {
        return src_lat;
    }

    public void setSrc_lat(double src_lat) {
        this.src_lat = src_lat;
    }

    public double getSrc_lon() {
        return src_lon;
    }

    public void setSrc_lon(double src_lon) {
        this.src_lon = src_lon;
    }

    public double getDestination_lat() {
        return destination_lat;
    }

    public void setDestination_lat(double destination_lat) {
        this.destination_lat = destination_lat;
    }

    public double getDestination_lon() {
        return destination_lon;
    }

    public void setDestination_lon(double destination_lon) {
        this.destination_lon = destination_lon;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExtra_distance() {
        return extra_distance;
    }

    public void setExtra_distance(String extra_distance) {
        this.extra_distance = extra_distance;
    }

    public String getDate_travel() {
        return date_travel;
    }

    public void setDate_travel(String date_travel) {
        this.date_travel = date_travel;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }
}
