package com.example.jmirza.firebaseauth.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.jmirza.firebaseauth.R;
import com.example.jmirza.firebaseauth.fragments.ComplainFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";
    private static final String CHANNEL_ID = "Notification_channel";
    private static final String CHANNEL_NAME = "Notification_name";
    private static final String CHANNEL_DES = "Notification_description";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DES);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String notificationTitle = null, notificationBody = null;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
           // Log.d(TAG, "Message User Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();

            sendNotification(notificationTitle, notificationBody);
        }

        super.onMessageReceived(remoteMessage);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
       // sendNotification(notificationTitle, notificationBody);
    }


    /**
     * Build a push notification for a complain message
     * @param title
     * @param message
     */

    private void sendNotification(String title, String message) {

        // Creates an Intent for the Activity
        Intent intent = new Intent(this, ComplainFragment.class);
        // Sets the Activity to start in a new, empty task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Creates the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

       // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Instantiate a Builder object.
        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this,CHANNEL_ID)
                //add properties to the builder
                .setAutoCancel(true)   //Automatically delete the notification
                .setSmallIcon(R.drawable.varsity_logo)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                //.setSubText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setOnlyAlertOnce(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(0,notificationBuilder.build());

    }
}
