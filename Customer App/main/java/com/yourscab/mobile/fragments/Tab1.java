package com.yourscab.mobile.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yourscab.mobile.MainActivity;
import com.yourscab.mobile.R;
import com.yourscab.mobile.data.InstantBookingQuickPlacesData;
import com.yourscab.mobile.subactivities.GeneralBookingActivity;

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
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Tab1 extends Fragment {

    public double lat1, lat2, lon1, lon2;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public List<String> getSlider_add_imageurl() {
        return slider_add_imageurl;
    }

    public void setSlider_add_imageurl(List<String> slider_add_imageurl) {
        this.slider_add_imageurl = slider_add_imageurl;
    }

    public List<String> slider_add_imageurl;
    public List<String> web_url_list;

    public List<String> getWeb_url_list() {
        return web_url_list;
    }

    public void setWeb_url_list(List<String> web_url_list) {
        this.web_url_list = web_url_list;
    }

    SliderLayout sliderLayout;


    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public int pincode;

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLon1() {
        return lon1;
    }

    public void setLon1(double lon1) {
        this.lon1 = lon1;
    }

    public double getLon2() {
        return lon2;
    }

    public void setLon2(double lon2) {
        this.lon2 = lon2;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    CardView cardView;

    LinearLayout from_layout, to_layout;
    TextView from_textview, to_textview;


    TextView journeyTime;
    TextView journeyDate;
    TextView journeyMonth;

    public Tab1() {
        // Required empty public constructor
    }

    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.tab1, container, false);

        // from layout
        from_layout = (LinearLayout) view.findViewById(R.id.from_linearlayout);
        from_textview = (TextView) view.findViewById(R.id.from_textview);

        to_layout = (LinearLayout) view.findViewById(R.id.to_linearlayout);
        to_textview = (TextView) view.findViewById(R.id.destination_textveiw);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        sliderLayout = (SliderLayout) view.findViewById(R.id.slider_adds);
        // here load the adds slides
        FirebaseDatabase.getInstance().getReference().child("slider_adds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> image_url_list = new ArrayList<>();
                List<String> title_list = new ArrayList<>();
                List<String> web_list = new ArrayList<>();
                web_list.clear();
                title_list.clear();
                image_url_list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String image_url = (String) ds.child("image_url").getValue();
                    String title = (String) ds.child("title").getValue();
                    String web_url = (String) ds.child("web_url").getValue();

                    web_list.add(web_url);
                    image_url_list.add(image_url);
                    title_list.add(title);

                }
                try {
                    setWeb_url_list(web_list);
                    setSlider_add_imageurl(image_url_list);
                    startSliderFromInternet(image_url_list.toArray(new String[image_url_list.size()]), title_list.toArray(new String[title_list.size()]));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try {
            // here loading the values for recent trend
            // InstantBookingQuickPlacesData instantBookingQuickPlacesData = new InstantBookingQuickPlacesData(getContext(), recyclerView, progressBar);

        } catch (Exception e) {
            e.printStackTrace();
        }


        double lat = ((MainActivity) getActivity()).getUser_lat();
        double lon = ((MainActivity) getActivity()).getUser_lon();

        FirebaseDatabase.getInstance().getReference().child("user_locations").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                    double lon = (double) dataSnapshot.child("lon").getValue();
                    String address = dataSnapshot.child("address").getValue().toString();
                    int pincode = Integer.parseInt(dataSnapshot.child("pincode").getValue().toString());

                    if (address != null && pincode != 0) {

                        setLat1(lat);
                        setLon1(lon);
                        setPincode(pincode);
                        from_textview.setText("" + address + " ," + pincode);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        view.findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String source_txt = from_textview.getText().toString().trim();
                final String to_text = to_textview.getText().toString().trim();
                final String journey_date = journeyDate.getText().toString() + ", " + journeyMonth.getText().toString();
                final String journey_time = journeyTime.getText().toString().trim();
                final String distance_txt = getDistance(getLat1(), getLon1(), getLat2(), getLon2());


                if (from_textview.getText().toString().contains("India") && to_textview.getText().toString().contains("India")) {

                    if (distance_txt != null) {


                        Intent intent = new Intent(getContext(), GeneralBookingActivity.class);
                        intent.putExtra("lat1", "" + getLat1());
                        intent.putExtra("lon1", "" + getLon1());
                        intent.putExtra("lat2", "" + getLat2());
                        intent.putExtra("lon2", "" + getLon2());
                        intent.putExtra("source", source_txt);
                        intent.putExtra("destination", to_text);
                        intent.putExtra("date", journey_date);
                        intent.putExtra("time", journey_time);
                        intent.putExtra("distance", distance_txt);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getContext(), "Error fetching distance...please try again...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Fill correct details...", Toast.LENGTH_LONG).show();
                }


            }
        });


        from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here enable the google play search places option and save it in the from_textview

                findPlace(view, 1);

            }
        });


        to_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hrere set the destinatino thing
                findPlace(view, 2);
            }
        });

        cardView = (CardView) view.findViewById(R.id.card_journey_time);
        journeyTime = (TextView) view.findViewById(R.id.journey_time);
        journeyDate = (TextView) view.findViewById(R.id.journey_date);
        journeyMonth = (TextView) view.findViewById(R.id.journey_month);


        Calendar c = Calendar.getInstance();

        String sDate = c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH)
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);


        journeyDate.setText("" + c.get(Calendar.DAY_OF_MONTH));
        journeyMonth.setText("" + getMonth(1 + c.get(Calendar.MONTH)));

        journeyTime.setText("" + convertData(c.get(Calendar.HOUR_OF_DAY)) + " : " + convertData(c.get(Calendar.MINUTE)));


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //Set the selected date here
                        //editText.setText(convertData(dayOfMonth)+":"+convertData(month+1)+":"+year);
                        journeyDate.setText(convertData(dayOfMonth));
                        journeyMonth.setText(getMonth(month + 1));
                        timeePicker();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        return view;
    }


    public void findPlace(View view, int k) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                    .setCountry("IN")
                    .build();

            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(getActivity());
            startActivityForResult(intent, k);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(getContext(), data);

                from_textview.setText("" + place.getName() + " ," + place.getAddress());

                String s = pincode("" + place.getAddress());

                setLat1(place.getLatLng().latitude);
                setLon1(place.getLatLng().longitude);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            } else {
                // error
            }
        } else if (requestCode == 2) {

            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                // it is the enter destination thing
                Place place = PlaceAutocomplete.getPlace(getContext(), data);


                setLat2(place.getLatLng().latitude);
                setLon2(place.getLatLng().longitude);

                to_textview.setText("" + place.getName() + " ," + place.getAddress());


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            } else {
                // error
            }


        }
    }


    public void timeePicker() {
        Calendar calendar2 = Calendar.getInstance();
        int hour = calendar2.get(Calendar.HOUR);
        int minute = calendar2.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                journeyTime.setText((convertData(hourOfDay)) + ":" + convertData(minute));

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public String convertData(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    public String getMonth(int month) {

        switch (month) {
            case 1:
                return "JAN";

            case 2:
                return "FEB";

            case 3:
                return "MAR";

            case 4:
                return "APR";

            case 5:
                return "MAY";

            case 6:
                return "JUN";

            case 7:
                return "JUL";

            case 8:
                return "AUG";

            case 9:
                return "SEP";

            case 10:
                return "OCT";

            case 11:
                return "NOV";

            case 12:
                return "DEC";

            default:
                return "FUC";

        }
    }

    public String pincode(String address) {
        String s = address.substring(address.length() - 14, address.length() - 7);
        if (isInteger(s)) {
            setPincode(Integer.parseInt(s));
        } else {
            setPincode(0);
        }

        return "" + getPincode();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
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

    public void startSliderFromInternet(String[] url, String[] title) {

        LinkedHashMap<String, String> url_maps = new LinkedHashMap<>();
        url_maps.clear();
        for (int i = 0; i < url.length; i++) {
            url_maps.put(title[i], url[i]);
        }
        int pos = 0;
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                            if (getSlider_add_imageurl().contains(slider.getUrl())) {
                                int pos = getSlider_add_imageurl().indexOf(slider.getUrl());
                                Toast.makeText(getContext(), "" + getWeb_url_list().get(pos), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            pos++;
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
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
