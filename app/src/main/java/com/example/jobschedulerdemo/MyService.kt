package com.example.jobschedulerdemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

import android.util.Log


class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("service", "----->MyService_onCreate")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("service", "----->onDestroy")
    }

}