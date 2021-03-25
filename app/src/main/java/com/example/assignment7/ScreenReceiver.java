package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenReceiver extends BroadcastReceiver {

    NotificationClass notification;
    @Override
    public void onReceive(Context context, Intent intent) {
        notification = new NotificationClass(context);
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            notification.createNotification("Screen Status","Your device is in idle mode");
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            notification.createNotification("Screen Status","Your device is in active mode");
        }
    }
}