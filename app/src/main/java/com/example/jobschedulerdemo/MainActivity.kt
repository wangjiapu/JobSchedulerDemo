package com.example.jobschedulerdemo

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.jobschedulerdemo.AlarmManagerDemo.AlarmManagerService
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
            Log.e("service","jobScheduler")
          // val intent= Intent(this,MyService::class.java)
         //   this.startService(intent)
            startService()
            //jobScheduler()
            //fireBaseJobDispatcher()
           // AlarmManagerService.startTimer(this)
    }
        button.setOnClickListener {
            startService()
            workManagerTest()
        }
        button2.setOnClickListener {
            startService()
            fireBaseJobDispatcher()
        }
    }

    /**
     * pixel 3-10 运行不稳定：前台时一直运行，后台时运行1分钟左右，Q切换回前台继续运行
     * 魅族note5-7.0：运行不稳定：前台时一直运行，后台时运行30秒左右，切换回前台并不运行
     *  三星sm-6.0： 不支持
     *
     *  小米note pro 7.0:运行不稳定：前台时一直运行，后台时运行service 死亡则结束，Q切换回前台继续运行
     *
     *  oppo 8.1 运行完美
     *
     */
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

    /**
     * 360 不支持
     * pixel 3-10 测试23分钟 稳定
     * 魅族note5-7.0：不支持
     * 三星sm-6.0： 不支持
     *
     * 小米note pro 7.0:不支持
     * oppo 8.1 运行完美
     */
    private fun fireBaseJobDispatcher() {
        Log.e("service","fireBaseJobDispatcher.start")
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
     *  pixel 3-10  15 分钟一次
     * 360 不自动调用
     * 魅族note5-7.0 不回调
     * 三星sm-6.0： 不支持
     * 小米note pro 7.0:切换至前台后支持
     *
     * oppo 8.1 运行完美
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun startService(){
        val intent= Intent(this,MyService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            this.startForegroundService(intent)
        }
        else{
            this.startService(intent)
        }

    }
}
