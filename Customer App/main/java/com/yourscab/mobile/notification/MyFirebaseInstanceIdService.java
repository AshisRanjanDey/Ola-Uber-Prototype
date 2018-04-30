package com.yourscab.mobile.notification;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;



public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_details");
            databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference = databaseReference.child("token");
            databaseReference.setValue(refreshedToken);
        }

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


    }

}
