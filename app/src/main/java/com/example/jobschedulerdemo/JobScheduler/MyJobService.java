package com.example.jobschedulerdemo.JobScheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.Log;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("service", "MyJobService");
        refreshJob(params);
        jobFinished(params, false);
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service", "MyJobService_onCreate");
    }


    private void refreshJob(JobParameters params) {
        try{
            JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                    .getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder mJobBuilder =
                    new JobInfo.Builder(10086,
                            new ComponentName(getPackageName(),
                                    MyJobService.class.getName()));
            mJobBuilder.setMinimumLatency(2000);
            mJobScheduler.schedule(mJobBuilder.build());
        }catch (Exception e){
            Log.e("service",e.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        Log.e("service", "MyJobService_onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("service", "MyJobService_onStopJob");
        return false;
    }
}
