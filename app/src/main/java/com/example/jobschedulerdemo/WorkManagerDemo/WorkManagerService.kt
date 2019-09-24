package com.example.jobschedulerdemo.WorkManagerDemo

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters


class WorkManagerService(@NonNull context: Context, @NonNull workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.e("service","WorkManagerService")
        return Result.success()
    }
}