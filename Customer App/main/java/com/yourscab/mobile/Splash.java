package com.yourscab.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yourscab.mobile.prototype.UserDetailsPRototype;
import com.yourscab.mobile.signup_signin.SignUp;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // if(FirebaseAuth.getInstance().getCurrentUser()!=null){
         //   startActivity(new Intent(getApplicationContext(),MainActivity.class));
        //}else{
          //  startActivity(new Intent(Splash.this, SignUp.class));
        //}
    }

}
