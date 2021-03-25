
package com.example.assignment7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

public class NetworkChangeReceiver extends BroadcastReceiver {

    String message;
    NotificationClass notification;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification = new NotificationClass(context);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if(capabilities!=null){
                    if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                        message = "Mobile data is active";
                    }
                    else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                        message = "WiFi is connected";
                    }
                    else {
                        message = "No internet connection";
                    }

                    notification.createNotification("Network Status",message);
                }
            }
        }
    }
}