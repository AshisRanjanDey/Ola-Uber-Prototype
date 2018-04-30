package com.yourscab.mobile.subSubActivities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.R;
import com.yourscab.mobile.prototype.TicketGeneralDriverKeLiyePrototype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SearchingDriversGeneralBooking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    public double lat, lon;

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

    public String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_drivers_general_booking);
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        String order_id = getIntent().getStringExtra("order_id");
        setOrder_id(order_id);
        setLat(Double.parseDouble(lat));
        setLon(Double.parseDouble(lon));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase.getInstance().getReference().child("instant_booking_orders").child(order_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String c_name = (String) dataSnapshot.child("user_name").getValue();
                            String c_phone = (String) dataSnapshot.child("phone_number").getValue();
                            String c_token = (String) dataSnapshot.child("user_token").getValue();
                            String c_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String time_travel = (String) dataSnapshot.child("time").getValue();
                            String travel_distance = (String) dataSnapshot.child("distance").getValue();
                            String destination_name = (String) dataSnapshot.child("destination").getValue();
                            double src_lat = (double) dataSnapshot.child("src_lat").getValue();
                            double src_lon = (double) dataSnapshot.child("src_lon").getValue();
                            double destination_lat = (double) dataSnapshot.child("destination_lat").getValue();
                            double destination_lon = (double) dataSnapshot.child("destination_lon").getValue();
                            String money = (String) dataSnapshot.child("price").getValue();
                            //String extradistance = () ye driver ke time pe nikalana hai
                            String extraDistance = "2 Km";
                            String date_travel = (String) dataSnapshot.child("date").getValue();
                            String source_name = (String) dataSnapshot.child("source").getValue();


                            TicketGeneralDriverKeLiyePrototype ticketGeneralDriverKeLiyePrototype = new TicketGeneralDriverKeLiyePrototype(c_name, c_phone, c_token, c_uid, travel_distance, destination_name, src_lat, src_lon, destination_lat, destination_lon, money, extraDistance, date_travel, source_name, "driver_token", "driver_id", "no", time_travel);

                            searchDrivers(ticketGeneralDriverKeLiyePrototype);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    public void searchDrivers(final TicketGeneralDriverKeLiyePrototype ticketGeneralDriverKeLiyePrototype) {
        FirebaseDatabase.getInstance().getReference().child("driver")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // now iterate through every loop

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String available = "no";
                            if (!dataSnapshot.exists()) {
                                break;
                            }
                            available = (String) ds.child("available").getValue();
                            if (available.equals("yes")) {
                                double driver_lat = (double) ds.child("current_location").child("lat").getValue();
                                double driver_lon = (double) ds.child("current_location").child("lon").getValue();
                                String driver_token1 = (String) ds.child("driver_token").getValue();
                                final String driver_id1 = ds.getKey();
                                ticketGeneralDriverKeLiyePrototype.driver_token = driver_token1;
                                ticketGeneralDriverKeLiyePrototype.driver_id = driver_id1;

                                String extra_distance = getDistance(ticketGeneralDriverKeLiyePrototype.src_lat, ticketGeneralDriverKeLiyePrototype.src_lon, driver_lat, driver_lon);
                                ticketGeneralDriverKeLiyePrototype.extra_distance = extra_distance;
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("driver").child("" + driver_id1);
                                databaseReference = databaseReference.child("order_list");
                                databaseReference.child("" + getOrder_id()).setValue(ticketGeneralDriverKeLiyePrototype)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "" + getOrder_id() + driver_id1, Toast.LENGTH_SHORT).show();


                                            }
                                        });


                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }


    public void saveInDb(final String driver_id, TicketGeneralDriverKeLiyePrototype ticketGeneralDriverKeLiyePrototype, DataSnapshot dataSnapshot) {

        DatabaseReference db = dataSnapshot.getRef();
        db = db.child("order_list");
        db.child("" + getOrder_id()).setValue(ticketGeneralDriverKeLiyePrototype)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "" + getOrder_id() + driver_id, Toast.LENGTH_SHORT).show();


                    }
                });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng user_pos = new LatLng(getLat(), getLon());
        googleMap.addMarker(new MarkerOptions().position(user_pos).title("I'm here!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(user_pos));
        googleMap.setMinZoomPreference(13.0f);
        googleMap.setMaxZoomPreference(18.0f);


    }

    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2) {
        final String[] parsedDistance = new String[1];
        final String[] response = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response[0] = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response[0]);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance[0] = "" + distance.getString("value");

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance[0];
    }


}
