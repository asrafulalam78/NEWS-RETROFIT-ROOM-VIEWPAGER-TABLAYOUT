package com.mdasrafulalam.news.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.mdasrafulalam.news.utils.Constants
import java.util.concurrent.TimeUnit

private const val TAG = "WorkerUtils"

@SuppressLint("MissingPermission")
fun makeStatusNotification(message: String, context: Context) {
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(com.mdasrafulalam.news.R.drawable.downloading)
        .setContentTitle(Constants.NOTIFICATION_TITLE)
        .setContentText(message)
        .setColor(context.resources.getColor(com.mdasrafulalam.news.R.color.swipe_color_1))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
}

class WorkManagerUtils {
    fun syncData(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiredNetworkType(NetworkType.UNMETERED).setRequiresStorageNotLow(true).build()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(SyncWorker::class.java, 300, TimeUnit.MINUTES, 10, TimeUnit.MINUTES)
                .setConstraints(constraints).addTag("Sync_Data").build()
        workManager.enqueueUniquePeriodicWork(
            "Sync_Data",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}