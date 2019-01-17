package com.arons.android5779_6274_2436_app2.Controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager_Factory;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.arons.android5779_6274_2436_app2.R;

import static com.arons.android5779_6274_2436_app2.Controller.appChannels.FOREGROUND_CHANNEL_ID;
import static com.arons.android5779_6274_2436_app2.Controller.appChannels.NEW_RIDE_CHANNEL_ID;


public class NewRideService extends Service {

    private final String TRANSFER_DRIVER_DETAILS = "transfer driver details";
    private NotificationManagerCompat notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();

        notificationManager = NotificationManagerCompat.from(this);
        startListenToDatabase();
    }


    private void startListenToDatabase() {
        Intent notificationIntent = new Intent(this, RidesManagerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Intent exitApplicationIntent = new Intent(this, ExitApp.class);
        PendingIntent exitApplicationPendingIntent = PendingIntent.getActivity(this, 0,
                exitApplicationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this,
                FOREGROUND_CHANNEL_ID)
                .setContentTitle("RideTaxi")
                .setContentText("RideTaxi is running in background")
                .setColor(Color.argb(255, 244, 140, 37))
                .setSmallIcon(R.drawable.ride_taxi_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_stop_grey, "Disconnect", exitApplicationPendingIntent)
                .build();

        startForeground(1, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DBManager backend = DBManager_Factory.getInstance();

        backend.notifyNewRide(new DBManager.NotifyDataChange<Ride>() {

            @Override
            public void onDataAdded(Ride ride) {

                Intent notificationIntent = new Intent(NewRideService.this, RidesManagerActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(NewRideService.this,
                        0, notificationIntent, 0);

                String message = "New ride to " + ride.getDestinationAddress().getAddress() + " available now";
                Notification newRideNotification = new NotificationCompat.Builder(NewRideService.this,
                        NEW_RIDE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ride_taxi_logo)
                        .setContentTitle("New Ride")
                        .setContentText(message)
                        .setColor(Color.argb(255, 244, 140, 37))
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(2, newRideNotification);
            }

            @Override
            public void OnDataChanged(Ride obj) {

            }

            @Override
            public void onDataRemoved(Ride obj) {

            }

            @Override
            public void onFailure(Exception exception) {
                // Toast.makeText(getBaseContext(), "error\n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return Service.START_NOT_STICKY;
    }
}



