package com.yourscab.mobile.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yourscab.mobile.MainActivity;
import com.yourscab.mobile.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class MyFirebaseMessagingService extends FirebaseMessagingService {


    Bitmap bitmap;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        //String url = remoteMessage.getData().get("");
        //   String url = "http://datastore03.rediff.com/h450-w670/thumb/5F655C625B695E62606E/rj9261brma5yqshs.D.0.sunny-leone-hardcore.jpg";
        bitmap = getBitmapfromUrl(remoteMessage.getData().get("image_url"));

        //sendOnlyNotification(remoteMessage.getData().get("title")+"   "+remoteMessage.getData().get("author"),remoteMessage.getData().get("TrueOrFalse"));

        sendNotification(remoteMessage.getData().get("title"),bitmap,remoteMessage.getData().get("body"));

    }


    private void sendOnlyNotification(String message,String TrueOrFalse){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri path = Uri.parse("android.resource://in.ac.nitsikkim.notificationsdemo/raw/ping");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setContentTitle(message)
                .setStyle(new NotificationCompat.InboxStyle()
                )/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(path)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    private void sendNotification(String messageBody, Bitmap image, String TrueOrFalse) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] v = {500,1500};
        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri path = Uri.parse("android.resource://com.yourscab.mobile/raw/notification");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.arrow_down))/*Notification icon image*/
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setContentTitle(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(path)
                .setAutoCancel(false)
                .setVibrate(v)
                .setSubText(TrueOrFalse)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}


