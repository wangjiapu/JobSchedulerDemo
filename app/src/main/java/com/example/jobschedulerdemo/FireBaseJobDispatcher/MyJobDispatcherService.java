package com.example.jobschedulerdemo.FireBaseJobDispatcher;


import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyJobDispatcherService extends JobService {
    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
       // refreshJob();
       // Log.e("service","MyJobDispatcherService");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("service","MyJobDispatcherService");
            }
        },0,2, TimeUnit.SECONDS);
        return false;
    }

    private void refreshJob() {
        FirebaseJobDispatcher dispatcher=new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job job=dispatcher.newJobBuilder()
                .setService(MyJobDispatcherService.class)
            .setTag("fireBaseJobDispatcher")
                .setTrigger(Trigger.executionWindow(0,5))
                .build();
        dispatcher.schedule(job);
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }
}
