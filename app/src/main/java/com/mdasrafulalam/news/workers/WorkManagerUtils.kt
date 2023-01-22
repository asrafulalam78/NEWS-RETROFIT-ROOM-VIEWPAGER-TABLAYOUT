package com.mdasrafulalam.news.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerUtils {
    fun syncData(context:Context){
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiredNetworkType(
            NetworkType.UNMETERED).setRequiresBatteryNotLow(true).setRequiresStorageNotLow(true).build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(SyncWorker::class.java,15, TimeUnit.MINUTES, 1,
            TimeUnit.MINUTES).setConstraints(constraints).addTag("Sync_Data").build()
        workManager.enqueue(periodicWorkRequest)
    }

}