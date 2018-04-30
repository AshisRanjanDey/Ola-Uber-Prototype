package com.yourscab.mobile.subactivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yourscab.mobile.R;
import com.yourscab.mobile.other.OtherMethods;
import com.yourscab.mobile.prototype.TicketGeneralBookingPrototype;
import com.yourscab.mobile.prototype.TicketGeneralDriverKeLiyePrototype;
import com.yourscab.mobile.road.Util;
import com.yourscab.mobile.subSubActivities.SearchingDriversGeneralBooking;

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
import java.util.ArrayList;
import java.util.List;

public class GeneralBookingActivity extends AppCompatActivity implements RoutingListener, OnMapReadyCallback {

    EditText name, phone, num_passenger;
    TextView details;
    GoogleMap map;
    public LatLng start, end;

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};


    private List<Polyline> polylines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_booking);
        final String lat1 = getIntent().getStringExtra("lat1");
        final String lat2 = getIntent().getStringExtra("lat2");
        final String lon1 = getIntent().getStringExtra("lon1");
        final String lon2 = getIntent().getStringExtra("lon2");
        final String date = getIntent().getStringExtra("date");
        final String time = getIntent().getStringExtra("time");
        final String source = getIntent().getStringExtra("source");
        final String destination = getIntent().getStringExtra("destination");
        setTitle("Confirm Booking");
        name = (EditText) findViewById(R.id.traveller_name);
        phone = (EditText) findViewById(R.id.traveller_phone);
        num_passenger = (EditText) findViewById(R.id.number_passenger);
        polylines = new ArrayList<>();


        setStart(new LatLng(Double.parseDouble(lat1), Double.parseDouble(lon1)));
        setEnd(new LatLng(Double.parseDouble(lat2), Double.parseDouble(lon2)));

        details = (TextView) findViewById(R.id.price_distance_details);
        final String distance = getIntent().getStringExtra("distance");

        details.setText("Distance: " + distance + "\nPrice : " + "Rs. 300");

        final String price = getPrice(distance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.confirm_booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here set the value for confirming it

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference = databaseReference.child("instant_booking_orders");
                databaseReference = databaseReference.push();
                final String ticket_id = databaseReference.getKey();
                String user_token = FirebaseInstanceId.getInstance().getToken();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (!(phone.getText().toString().isEmpty() || name.getText().toString().isEmpty())) {
                    OtherMethods otherMethods = new OtherMethods();
                    TicketGeneralBookingPrototype ticketGeneralBookingPrototype = new TicketGeneralBookingPrototype(
                            destination, source, date, time, phone.getText().toString(), "" + name.getText().toString(), Double.parseDouble(lat1), Double.parseDouble(lon1), distance,
                            price, otherMethods.getDateTime(), ticket_id, Double.parseDouble(lat2),
                            Double.parseDouble(lon2), uid, user_token, "no", "no", "N.A");

                    databaseReference.setValue(ticketGeneralBookingPrototype)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                    db = db.child("user_orders");
                                    db = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    db = db.child(ticket_id);
                                    db.setValue("instant");
                                    Toast.makeText(getApplicationContext(), "Successfully booked", Toast.LENGTH_LONG).show();


                                    Intent intent = new Intent(getApplicationContext(), SearchingDriversGeneralBooking.class);
                                    intent.putExtra("order_id", ticket_id);
                                    intent.putExtra("lon", lon1);
                                    intent.putExtra("lat", lat1);
                                    startActivity(intent);


                                    //               onBackPressed();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Unable  to book the ticket, please try again", Toast.LENGTH_LONG).show();
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Phone Number or Name field is Empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String person_name = dataSnapshot.child("name").getValue().toString().trim();
                    name.setText(person_name);
                    phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    num_passenger.setText("1");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (getStart() != null) {
            routingBuilderFunciton(getStart(), getEnd());

            Toast.makeText(getApplicationContext(), "" + getStart() + getEnd(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "142", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera

        if (map != null) {
            map.clear();
        }
    }


    public void routingBuilderFunciton(LatLng start, LatLng end) {
        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();
    }


    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int i) {


        CameraUpdate center;

        center = CameraUpdateFactory.newLatLng(getStart());


        map.moveCamera(center);


        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int j = 0; j < route.size(); j++) {

            //In case of more than 5 alternative routes
            int colorIndex = j % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + j * 3);
            polyOptions.addAll(route.get(j).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            polylines.add(polyline);

            // Toast.makeText(getApplicationContext(),"Route "+ (j+1) +": distance - "+ route.get(j).getDistanceValue()+": duration - "+ route.get(j).getDurationValue(),Toast.LENGTH_SHORT).show();
        }


        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(getStart());
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        options.title("Source");
        map.addMarker(options);


        // End marker
        options = new MarkerOptions();
        options.position(getEnd());
        options.title("Destination");
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
        map.setMinZoomPreference(8.0f);


    }


    public String getDistance1(final String lat1, final String lon1, final String lat2, final String lon2) {
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
                    parsedDistance[0] = distance.getString("text");

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingCancelled() {

    }

    public void sendRequest() {
        if (Util.Operations.isOnline(this)) {
            //route();
        } else {
            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Unregister for location callbacks:
    }

    public String getOrderId() {
        String key = "";

        return key;
    }

    public String getPrice(String distance) {
        String price = "Rs. 200";


        return price;
    }


}
