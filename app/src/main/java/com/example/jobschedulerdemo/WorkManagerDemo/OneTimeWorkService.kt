package com.example.jobschedulerdemo.WorkManagerDemo

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeWorkService(@NonNull context: Context, @NonNull workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}