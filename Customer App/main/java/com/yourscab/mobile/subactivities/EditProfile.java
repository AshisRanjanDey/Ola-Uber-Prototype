package com.yourscab.mobile.subactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yourscab.mobile.R;

public class EditProfile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String name = getIntent().getStringExtra("name");
        String phone_number = getIntent().getStringExtra("phone");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
