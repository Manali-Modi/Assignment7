package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Spinner spnnotify, spnalarm;
    Button btnnotify, btnalarm;

    String[] diffNoti = {"Notify while in charge mode","Notify when battery is low","Notify when network state change",
    "Notify on GPS enable/disable","Notify when device is idle"};
    String[] alarmSet = {"Set alarm on predefined time","Set alarm after certain amount of time"};

    IntentFilter intentFilter;
    AlarmManager alarmManager;

    Context ctx =this;
    Intent intent;
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnnotify = findViewById(R.id.spnnotify);
        spnalarm = findViewById(R.id.spnalarm);
        btnnotify = findViewById(R.id.btnnotify);
        btnalarm = findViewById(R.id.btnalarm);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        intentFilter = new IntentFilter();
        spnnotify.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,diffNoti));
        spnalarm.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,alarmSet));

        btnnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = spnnotify.getSelectedItemPosition();
                switch (i){
                    case 0:
                        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
                        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
                        registerReceiver(new ChargingModeReceiver(),intentFilter);
                        break;
                    case 1:
                        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
                        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
                        registerReceiver(new BatteryStateReceiver(),intentFilter);
                        break;
                    case 2:
                        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                        registerReceiver(new NetworkChangeReceiver(),intentFilter);
                        break;
                    case 3:
                        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
                        registerReceiver(new GPSReceiver(),intentFilter);
                        break;
                    case 4:
                        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
                        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
                        registerReceiver(new ScreenReceiver(),intentFilter);
                        break;
                }
            }
        });

        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (spnalarm.getSelectedItemPosition()){
                    case 0:
                        GregorianCalendar gc = new GregorianCalendar();
                        int DefaultHour = gc.get(Calendar.HOUR);
                        int DefaultMin = gc.get(Calendar.MINUTE);
                        TimePickerDialog tpd = new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int h, int m) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,h);
                                c.set(Calendar.MINUTE,m);
                                c.set(Calendar.SECOND,0);
                                updateTimeSet(c);
                            }
                        },DefaultHour,DefaultMin,false);
                        tpd.show();
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, AlarmReceiver.class);
                        pi = PendingIntent.getBroadcast(ctx,3,intent,0);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 20000,0,pi);
                        break;
                }
            }
        });
    }

    private void updateTimeSet(Calendar c) {
        //String timeSet = "Alarm set for: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c);
        Toast.makeText(ctx,"Alarm Set",Toast.LENGTH_LONG).show();

        intent = new Intent(MainActivity.this,PredefinedAlarmReceiver.class);
        pi = PendingIntent.getBroadcast(ctx,4,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(new ChargingModeReceiver());
        unregisterReceiver(new BatteryStateReceiver());
        unregisterReceiver(new NetworkChangeReceiver());
        unregisterReceiver(new GPSReceiver());
        unregisterReceiver(new ScreenReceiver());
        alarmManager.cancel(pi);
    }
}