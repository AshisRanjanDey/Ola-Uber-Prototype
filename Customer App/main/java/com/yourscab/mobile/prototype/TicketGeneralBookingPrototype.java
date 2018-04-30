package com.yourscab.mobile.prototype;



public class TicketGeneralBookingPrototype {
    public String destination,source,date,time,phone_number,user_name;
    double src_lat,src_lon;
    String         distance,price;
    double destination_lat,destination_lon;
    String order_date;
    String ticket_id;
    String user_id;
    String user_token;
    String isCancelled;
    String driverAssigned;
    String driver_token;

    public String getDriver_token() {
        return driver_token;
    }

    public void setDriver_token(String driver_token) {
        this.driver_token = driver_token;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getDriverAssigned() {
        return driverAssigned;
    }

    public void setDriverAssigned(String driverAssigned) {
        this.driverAssigned = driverAssigned;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public TicketGeneralBookingPrototype(String destination, String source, String date, String time,
                                         String phone_number, String user_name, double src_lat, double src_lon,
                                         String distance, String price, String order_date, String ticket_id,
                                         double destination_lat, double destination_lon, String user_id,
                                         String user_token, String isCancelled, String driverAssigned,
                                         String driver_token) {
        this.destination = destination;
        this.source = source;
        this.date = date;
        this.time = time;
        this.phone_number = phone_number;
        this.user_name = user_name;
        this.src_lat = src_lat;
        this.src_lon = src_lon;
        this.distance = distance;
        this.price = price;
        this.order_date = order_date;
        this.ticket_id = ticket_id;
        this.destination_lat = destination_lat;
        this.destination_lon = destination_lon;
        this.user_id = user_id;
        this.user_token = user_token;
        this.isCancelled = isCancelled;
        this.driverAssigned = driverAssigned;
        this.driver_token = driver_token;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}
