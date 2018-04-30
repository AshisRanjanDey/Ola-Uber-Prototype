package com.yourscab.mobile.subactivities;

import android.content.Context;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.R;
import com.yourscab.mobile.road.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TouristPackageDetails extends AppCompatActivity implements RoutingListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnMapReadyCallback {
    TextView starting_point, ending_point, total_distance, total_time, price, desc;
    SliderLayout sliderLayout;
    ProgressBar progressBar;

    public static final int REQUEST_CHECK_SETTINGS = 44;
    List<LatLng> latLngs;
    List<String> haults;

    public List<String> getHaults() {
        return haults;
    }

    public void setHaults(List<String> haults) {
        this.haults = haults;
    }

    public List<LatLng> getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(List<LatLng> latLngs) {
        this.latLngs = latLngs;
    }

    private GoogleMap map;

    public LatLng start;
    public LatLng end;

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


    private static final LatLngBounds BOUNDS_JAMAICA = new LatLngBounds(new LatLng(-57.965341647205726, 144.9987719580531),
            new LatLng(72.77492067739843, -9.998857788741589));
    private List<Polyline> polylines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_package_details);

        String title = getIntent().getStringExtra("title");
        String node = getIntent().getStringExtra("node");

        setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        polylines = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        starting_point = (TextView) findViewById(R.id.touristdetails_startingpointtv);
        ending_point = (TextView) findViewById(R.id.touristdetails_endingpointtv);
        total_distance = (TextView) findViewById(R.id.touristdetails_totaldistance);
        total_time = (TextView) findViewById(R.id.touristdetails_totaltimetaken);
        price = (TextView) findViewById(R.id.touristdetails_price);
        desc = (TextView) findViewById(R.id.touristdetails_description);

        sliderLayout = (SliderLayout) findViewById(R.id.slider_touristpackagedtails);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        try {
            FirebaseDatabase.getInstance().getReference().child("package").child(node).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot = dataSnapshot.child("details");
                    String desc_str = dataSnapshot.child("desc").getValue().toString().trim();
                    int total_price = Integer.parseInt(dataSnapshot.child("price").getValue().toString().trim());
                    int total_hours = Integer.parseInt(dataSnapshot.child("total_hours").getValue().toString().trim());


                    // source destinaiton
                    Double destination_lat = Double.valueOf(dataSnapshot.child("destination").child("lat").getValue().toString().trim());
                    Double destination_lon = Double.valueOf(dataSnapshot.child("destination").child("lon").getValue().toString().trim());
                    String destination_name = dataSnapshot.child("destination").child("name").getValue().toString().trim();

                    // source name
                    String source_name = dataSnapshot.child("source").child("name").getValue().toString().trim();
                    Double source_lat = Double.valueOf(dataSnapshot.child("source").child("lat").getValue().toString().trim());
                    Double source_lon = Double.valueOf(dataSnapshot.child("source").child("lon").getValue().toString().trim());


                    setStart(new LatLng(source_lat, source_lon));
                    setEnd(new LatLng(destination_lat, destination_lon));

                    starting_point.setText("Starting Point: " + source_name);
                    ending_point.setText("Ending Point : " + destination_name);

                    total_time.setText("Total Time of Journey: " + total_hours + " hours.");
                    price.setText("Total Price : " + total_price);
                    desc.setText(desc_str);

                    // now for adding haults

                    List<LatLng> latLngs = new ArrayList<>();
                    latLngs.clear();
                    List<String> names = new ArrayList<>();
                    names.clear();

                    DataSnapshot d2 = dataSnapshot.child("haults");
                    for (DataSnapshot d : d2.getChildren()) {
                        Double lat = Double.valueOf(d.child("lat").getValue().toString().trim());
                        Double lon = Double.valueOf(d.child("lon").getValue().toString().trim());
                        String name = d.child("name").getValue().toString().trim();

                        latLngs.add(new LatLng(lat, lon));
                        names.add(name);

                    }

                    if (latLngs.size() > 0 && latLngs.size() == names.size()) {
                        setLatLngs(latLngs);
                        setHaults(names);
                    }

                    // now add sliders
                    List<String> urls = new ArrayList<>();
                    List<String> title = new ArrayList<>();
                    urls.clear();
                    title.clear();

                    DataSnapshot d = dataSnapshot.child("slider");
                    for (DataSnapshot ds : d.getChildren()) {
                        String url = ds.child("url").getValue().toString().trim();
                        String head = ds.child("title").getValue().toString().trim();
                        urls.add(url);
                        title.add(head);


                    }

                    startSliderFromInternet(urls.toArray(new String[urls.size()]), title.toArray(new String[title.size()]));
                    progressBar.setVisibility(View.INVISIBLE);
                    routingBuilderFunciton();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }


    public void routingBuilderFunciton() {
        LatLng start = getStart();
        LatLng end = getEnd();
        if (getLatLngs().size() > 2) {

            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(start, getLatLngs().get(1), end)
                    .build();
            routing.execute();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(Routing.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(start, end)
                    .build();
            routing.execute();
        }

    }


    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int i) {


        CameraUpdate center;

        if (getHaults().size() > 2) {
            center = CameraUpdateFactory.newLatLng(getLatLngs().get(1));
        } else {
            center = CameraUpdateFactory.newLatLng(getStart());
        }

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
        options.title("" + getHaults().get(0));
        map.addMarker(options);


        // End marker
        options = new MarkerOptions();
        options.position(getEnd());
        options.title("" + getHaults().get(getHaults().size() - 1));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
        map.setMinZoomPreference(10.0f);


        if (getLatLngs().size() > 0) {
            // here the should be only start and end


        } else {

            for (int p = 0; p < getLatLngs().size(); p++) {
                if (p > 0 && p < (getLatLngs().size() - 1)) {

                    options = new MarkerOptions();
                    options.position(getLatLngs().get(p));
                    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_map_icon_marker));
                    options.title(getHaults().get(p));
                    map.addMarker(options);
                    map.setMinZoomPreference(10.0f);

                }
            }

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

    public void startSliderFromInternet(String[] url, String[] title) {

        LinkedHashMap<String, String> url_maps = new LinkedHashMap<>();
        url_maps.clear();
        for (int i = 0; i < url.length; i++) {
            url_maps.put(title[i], url[i]);
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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



    /*
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(TouristPackageDetails.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }
        */
}
