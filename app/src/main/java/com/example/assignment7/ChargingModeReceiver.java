package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChargingModeReceiver extends BroadcastReceiver {

    NotificationClass notification;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification = new NotificationClass(context);
        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            notification.createNotification("Charging Mode Status","Your device is charging");
        }
        else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            notification.createNotification("Charging Mode Status","Your device charger is unplugged");
        }
    }
}