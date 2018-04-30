package com.yourscab.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yourscab.mobile.R;


import com.yourscab.mobile.bottomNavigation.BottomNavigationBehavior;
import com.yourscab.mobile.fragments.Tab1;
import com.yourscab.mobile.fragments.Tab2;
import com.yourscab.mobile.fragments.Tab3;
import com.yourscab.mobile.fragments.Tab4;
import com.yourscab.mobile.prototype.UserLocationPrototype;
import com.yourscab.mobile.trial.TrialLocationGpsUpdates;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    public double user_lat, user_lon;
    public String user_address;

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public double getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(double user_lat) {
        this.user_lat = user_lat;
    }

    public double getUser_lon() {
        return user_lon;
    }

    public void setUser_lon(double user_lon) {
        this.user_lon = user_lon;
    }

    private ActionBar toolbar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            getSupportActionBar().hide();
        }catch (Exception e){
            e.printStackTrace();
        }

        displayLocationSettingsRequest(MainActivity.this);       //show the gps enable request dialog
        startLocationUpdates(MainActivity.this);                // start the location updates

        // now set the common notification to all of users here
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        // set the token if it is refreshed
        saveTheToken();

        //     toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();


        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        //toolbar.setTitle("Shop");
                        fragment = new Tab1();
                        getSupportActionBar().hide();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_gifts:
                        //toolbar.setTitle("Tourist");
                        fragment = new Tab2();
                        getSupportActionBar().hide();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_cart:
                        // toolbar.setTitle("Cart");
                        fragment = new Tab3();
                        getSupportActionBar().hide();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        //   toolbar.setTitle("Profile");
                        fragment = new Tab4();
                        getSupportActionBar().hide();
                        loadFragment(fragment);
                        return true;
                }

                return false;
            }
        };


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(false);
        }

        // attaching bottom sheet behaviour - hide / show on scroll

        // load the store fragment by default
        //       toolbar.setTitle("Instant booking");
        loadFragment(new Tab1());


    }


    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void saveTheToken() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_details");
            databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference = databaseReference.child("token");
            databaseReference.setValue(FirebaseInstanceId.getInstance().getToken());
        }
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
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
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    public void startLocationUpdates(Context context) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void getLastLocation(Context context) {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(context);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {
        saveinDb(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void saveinDb(Location location) {

        setUser_lat(location.getLatitude());
        setUser_lon(location.getLongitude());


        try {

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            addresses = geocoder.getFromLocation(getUser_lat(), getUser_lon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            int postalCode;
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            try {
                postalCode = Integer.parseInt(addresses.get(0).getPostalCode());
            } catch (Exception e) {
                postalCode = 737139;
            }
            String knownName = addresses.get(0).getFeatureName();

            setUser_address(address);

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference = databaseReference.child("user_locations");
                databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                if (getUser_lon() != 0) {
                    UserLocationPrototype userLocationPrototype = new UserLocationPrototype(getUser_lat(), getUser_lon(), getUser_address(), postalCode);
                    databaseReference.setValue(userLocationPrototype);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
