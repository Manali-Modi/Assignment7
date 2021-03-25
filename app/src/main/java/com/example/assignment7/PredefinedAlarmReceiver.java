package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PredefinedAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationClass notification = new NotificationClass(context);
        notification.createNotification("Alarm at your time","Hello, you set this alarm");
    }
}