package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryStateReceiver extends BroadcastReceiver {

    NotificationClass notification;
    @Override
    public void onReceive(Context context, Intent intent) {
        notification = new NotificationClass(context);
        if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
            notification.createNotification("Battery Status","Your device battery is low");
        }
        else if(intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)){
            notification.createNotification("Battery Status","Your device battery is okay");
        }
    }
}