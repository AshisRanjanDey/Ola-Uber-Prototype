package com.yourscab.mobile.prototype;



public class MyOrdersBookingPrototype {
    public String destination,source,date_booking;
    public String date_travel,ticket_id;
    public String  distance,phone,price,time_travel,user_name;

    public MyOrdersBookingPrototype(){}
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime_travel() {
        return time_travel;
    }

    public void setTime_travel(String time_travel) {
        this.time_travel = time_travel;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public double destination_lat,destination_lon,src_lat,src_lon;


    public MyOrdersBookingPrototype(String destination, String source, String date_booking,
                                    String date_travel, String ticket_id) {
        this.destination = destination;
        this.source = source;
        this.date_booking = date_booking;
        this.date_travel = date_travel;
        this.ticket_id = ticket_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate_booking() {
        return date_booking;
    }

    public void setDate_booking(String date_booking) {
        this.date_booking = date_booking;
    }

    public String getDate_travel() {
        return date_travel;
    }

    public void setDate_travel(String date_travel) {
        this.date_travel = date_travel;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}
