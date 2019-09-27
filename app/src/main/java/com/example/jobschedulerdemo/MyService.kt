package com.example.jobschedulerdemo

import android.app.*
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.jobschedulerdemo.JobScheduler.MyJobService
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.jobschedulerdemo.AlarmManagerDemo.AlarmManagerService


class MyService : Service() {

    private final val CHANNEL_ID = "TEST_SERVICE_ID"
    private final val CHANNEL_NAME = "渠道一"

    private final val contentSub = "小标题"
    private final val contentTitle = "标题"
    private final val contentText = "测试前台服务"
    var notification: Notification? = null
    var builder: Notification.Builder? = null

    private var mServiceComponent: ComponentName? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
       // AlarmManagerService.startTimer(this)
        setForegroundService()
    }

    private fun jobScheduler() {
        mServiceComponent = ComponentName(packageName, MyJobService::class.java.name)
        val builder = JobInfo.Builder(10086, mServiceComponent!!)
        builder.setMinimumLatency(1000)
        builder.setPersisted(true)
        val mJobScheduler = this.getSystemService(Context.JOB_SCHEDULER_SERVICE) as (JobScheduler)
        mJobScheduler.schedule(builder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("service", "----->onDestroy")
    }

    fun setForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            chan.enableLights(true)
            chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)

            manager.createNotificationChannel(chan)

             builder = Notification . Builder (this, CHANNEL_ID)
            notification = builder!!
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(contentText)
                .setSubText(contentSub)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentTitle(contentTitle)
                .build()
        } else {
            val i =  Intent()
            val p = PendingIntent . getActivity (this, 1, i, 0)
            notification =  Notification . Builder (applicationContext)
                .setContentInfo(contentSub)
                .setSubText(contentSub)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(p)
                .build()
        }
        startForeground(1, notification)
        jobScheduler()
        //AlarmManagerService.startTimer(this)
    }

}