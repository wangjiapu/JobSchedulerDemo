package com.example.jobschedulerdemo

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.jobschedulerdemo.FireBaseJobDispatcher.MyJobDispatcherService
import com.example.jobschedulerdemo.JobScheduler.MyJobService
import com.example.jobschedulerdemo.WorkManagerDemo.WorkManagerService
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Trigger
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private var mServiceComponent: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.setOnClickListener {
            jobScheduler()
        }
        button.setOnClickListener {
            workManagerTest()
        }
        button2.setOnClickListener {
            startService()
            fireBaseJobDispatcher()
        }
    }

    private fun jobScheduler(){
        mServiceComponent = ComponentName(packageName, MyJobService::class.java.name)
        val builder = JobInfo.Builder(10086, mServiceComponent!!)
        builder.setMinimumLatency(1000)
        val mJobScheduler = this.getSystemService(Context.JOB_SCHEDULER_SERVICE) as (JobScheduler)
        mJobScheduler.schedule(builder.build())
    }
    /**
     * 使用firebase
     */
    private fun fireBaseJobDispatcher() {
        val dispatcher=FirebaseJobDispatcher(GooglePlayDriver(this))
        val job=dispatcher.newJobBuilder()
            .setService(MyJobDispatcherService::class.java)
            .setTag("fireBaseJobDispatcher")
            .setTrigger(Trigger.executionWindow(0,5))
            .build()
        dispatcher.schedule(job)
        //dispatcher.mustSchedule(job)
    }

    /**
     * 使用workManger
     *
     */
    private fun workManagerTest() {
        val data = Data.Builder()
            .putString("work", "workManagerTest")
            .build()
        //约束条件
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //执行一次的work
        // val OneTimeRequest = OneTimeWorkRequest.Builder(OneTimeWorkService::class.java)
        //最小重复时间小于15min,这就有点坑。。。。
        val periodicRequest =
                PeriodicWorkRequest.Builder(WorkManagerService::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag("workManagerTest")
                .setInputData(data)
                .build()
        val operation=WorkManager.getInstance(this).enqueue(periodicRequest)
    }

    fun startService(){
        val intent= Intent(this,MyService::class.java)
        this.startService(intent)
    }
}
