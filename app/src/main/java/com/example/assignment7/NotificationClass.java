package com.example.assignment7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationClass {
    NotificationManager manager;
    Context context;
    NotificationClass(Context ctx){
        context = ctx;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(String title, String msg){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ID1","All Notification",NotificationManager.IMPORTANCE_HIGH);
            if (manager!=null)
                manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"ID1")
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(android.R.drawable.star_on)
                .setAutoCancel(true);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,55,intent,0);
        builder.setContentIntent(pendingIntent);

        manager.notify(1,builder.build());
    }
}
