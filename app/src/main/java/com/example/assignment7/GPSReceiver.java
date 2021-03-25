package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class GPSReceiver extends BroadcastReceiver {

    NotificationClass notification;
    String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification = new NotificationClass(context);
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnable)
            message = "Your device GPS is on";
        else
            message = "Your device GPS is off";
        notification.createNotification("GPS Status",message);
    }
}