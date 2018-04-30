package com.yourscab.mobile.signup_signin;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yourscab.mobile.MainActivity;
import com.yourscab.mobile.R;
import com.yourscab.mobile.Splash;
import com.yourscab.mobile.other.OtherMethods;
import com.yourscab.mobile.prototype.UserDetailsPRototype;

import java.util.Arrays;

public class SignUp extends AppCompatActivity {

    EditText editText;
    EditText email_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(SignUp.this,MainActivity.class));
            finish();
        }

        editText = (EditText) findViewById(R.id.username);
        email_et = (EditText) findViewById(R.id.email);


        try {
            Account[] accounts = AccountManager.get(SignUp.this).getAccounts();
            for (Account account : accounts) {
                if (account.name.contains("@")) {
                    email_et.setText(account.name);
                    break;

                } else {
                    continue;
                }

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
        }


            findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!editText.getText().toString().isEmpty()) {


                        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build())).build(), 1000);


                    }

                }
            });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1000) {

                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (resultCode == RESULT_OK) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {


                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference = databaseReference.child("user_details");

                        if (email_et.getText().toString().trim().isEmpty()) {
                            email_et.setText("Enter Your Mail");
                        }
                        OtherMethods otherMethods = new OtherMethods();

                        UserDetailsPRototype userDetailsPRototype = new UserDetailsPRototype("" + FirebaseInstanceId.getInstance().getToken(), editText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), "some.jpg", otherMethods.getDateTime(), email_et.getText().toString().trim());
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            databaseReference = databaseReference.child("" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                        databaseReference.setValue(userDetailsPRototype).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getApplicationContext(), "Successfully Registered...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, MainActivity.class).putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()));
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                //startActivity(new Intent(SignUp.this, MainActivity.class).putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()));
                                finish();

                            }
                        });
                        return;

                    } else {
                        if (response == null) {
                            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                            Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                            Toast.makeText(this, "unkown Error", Toast.LENGTH_SHORT).show();

                            return;
                        }
                    }
                }
            }
        }

    }
