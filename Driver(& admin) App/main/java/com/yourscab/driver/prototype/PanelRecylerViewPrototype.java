package com.yourscab.driver.prototype;



public class PanelRecylerViewPrototype {
    public String customer_name, customer_phone, customer_uid, date_travel;
    public double destination_lat, destination_lon, source_lat, source_lon;
    public String destination_name, source_name, travel_distance, extra_distance, money, isConfirmed;


    public PanelRecylerViewPrototype(String customer_name, String customer_phone, String customer_uid, String date_travel, double destination_lat, double destination_lon, double source_lat, double source_lon, String destination_name, String source_name, String travel_distance, String extra_distance, String money, String isConfirmed) {
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.customer_uid = customer_uid;
        this.date_travel = date_travel;
        this.destination_lat = destination_lat;
        this.destination_lon = destination_lon;
        this.source_lat = source_lat;
        this.source_lon = source_lon;
        this.destination_name = destination_name;
        this.source_name = source_name;
        this.travel_distance = travel_distance;
        this.extra_distance = extra_distance;
        this.money = money;
        this.isConfirmed = isConfirmed;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_uid() {
        return customer_uid;
    }

    public void setCustomer_uid(String customer_uid) {
        this.customer_uid = customer_uid;
    }

    public String getDate_travel() {
        return date_travel;
    }

    public void setDate_travel(String date_travel) {
        this.date_travel = date_travel;
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

    public double getSource_lat() {
        return source_lat;
    }

    public void setSource_lat(double source_lat) {
        this.source_lat = source_lat;
    }

    public double getSource_lon() {
        return source_lon;
    }

    public void setSource_lon(double source_lon) {
        this.source_lon = source_lon;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getTravel_distance() {
        return travel_distance;
    }

    public void setTravel_distance(String travel_distance) {
        this.travel_distance = travel_distance;
    }

    public String getExtra_distance() {
        return extra_distance;
    }

    public void setExtra_distance(String extra_distance) {
        this.extra_distance = extra_distance;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
