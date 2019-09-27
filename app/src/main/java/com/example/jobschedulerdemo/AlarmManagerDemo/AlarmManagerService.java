package com.example.jobschedulerdemo.AlarmManagerDemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class AlarmManagerService {


    public static void startTimer(Context context) {
        Intent intent = new Intent(context,
                TimeChangeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 0, intent, 0);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
       // calendar.add(Calendar.SECOND, 10);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000, sender);
    }


}
