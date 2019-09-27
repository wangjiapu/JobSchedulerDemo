package com.example.jobschedulerdemo.AlarmManagerDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public  class TimeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("service","TimeChangeReceiver");
//            Intent i = new Intent(ACTION);
//            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            PendingIntent sender = PendingIntent.getBroadcast(context, CLOCK_ID, i, PendingIntent.FLAG_CANCEL_CURRENT);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                am.setWindow(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, 100, sender);
//            }
    }
}