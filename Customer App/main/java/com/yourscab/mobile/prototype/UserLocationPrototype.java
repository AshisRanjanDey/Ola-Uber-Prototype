package com.yourscab.mobile.prototype;


public class UserLocationPrototype {
    public double  lat,lon;
    public String address;
    int pincode;

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserLocationPrototype(double lat, double lon, String address,int pincode) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.pincode = pincode;
    }
}
