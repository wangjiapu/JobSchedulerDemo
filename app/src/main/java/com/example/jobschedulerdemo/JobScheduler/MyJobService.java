package com.example.jobschedulerdemo.JobScheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.example.jobschedulerdemo.AlarmManagerDemo.AlarmManagerService;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
//        Log.e("service", "MyJobService");
//        refreshJob(params);
//        jobFinished(params, false);

//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("service", "Executors");
//            }
//        }, 0, 2, TimeUnit.SECONDS);
//        return false;
       // AlarmManagerService.startTimer(this);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
    }


    private void refreshJob(JobParameters params) {
        try {
            JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                    .getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder mJobBuilder =
                    new JobInfo.Builder(10086,
                            new ComponentName(getPackageName(),
                                    MyJobService.class.getName()));
            mJobBuilder.setMinimumLatency(2000);
            mJobScheduler.schedule(mJobBuilder.build());
        } catch (Exception e) {
            Log.e("service", e.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        Log.e("service", "MyJobService_onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
