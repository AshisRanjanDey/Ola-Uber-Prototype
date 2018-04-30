package com.yourscab.mobile.prototype;



public class InstantBookingQuickPlacesPrototype {

    public String image_url, name;
    public double lat, lon;


    public String getImage_url() {
        return image_url;
    }


    public InstantBookingQuickPlacesPrototype(String image_url, String name, double lat, double lon) {
        this.image_url = image_url;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
