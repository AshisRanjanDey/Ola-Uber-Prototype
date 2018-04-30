package com.yourscab.driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    EditText driver_id, driver_phone;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //now sub

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String driverId = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@gmail.com","");
            Intent intent = new Intent(getApplicationContext(),Panel.class);
            intent.putExtra("driver_id",driverId);
            startActivity(intent);
        }

        init();

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String str_driver_id = driver_id.getText().toString().trim().toLowerCase();
                final String str_driver_phone = driver_phone.getText().toString().trim();

                if (str_driver_id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "One or more fields are emtpy", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Signing in...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    FirebaseDatabase.getInstance().getReference().child("driver").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(str_driver_id)) {

                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword("" + str_driver_id + "@gmail.com", "yourscab").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Toast.makeText(getApplicationContext(), "57", Toast.LENGTH_SHORT).show();


                                        }
                                    });


                                }
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), Panel.class);
                                intent.putExtra("driver_id", str_driver_id);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid driver id...try again ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });


    }

    public void init() {
        driver_id = (EditText) findViewById(R.id.driver_id);
        driver_phone = (EditText) findViewById(R.id.driver_password);


    }
}
