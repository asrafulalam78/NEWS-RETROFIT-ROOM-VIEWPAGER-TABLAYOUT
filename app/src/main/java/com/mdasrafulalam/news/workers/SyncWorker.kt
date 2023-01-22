package com.mdasrafulalam.news.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.R
import com.mdasrafulalam.news.utils.Constants

class SyncWorker(context: Context, params:WorkerParameters): Worker(context, params), ViewModelStoreOwner {

    private lateinit var viewModel: NewsViewmodel
    private val appViewModelStore: ViewModelStore by lazy {         ViewModelStore()     }
    @SuppressLint("WrongThread")
    override fun doWork(): Result {
        viewModel =  ViewModelProvider(this)[NewsViewmodel::class.java]
        viewModel.refreshData()
//        sendNotification("There are some breaking news!")
        makeStatusNotification("There are some breaking news!", applicationContext)
        return Result.success()
    }
    private fun sendNotification(name: String?) {
        val CHANNEL_ID = "my_channel"
        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.downloading)
            .setContentTitle("News Updated!!!")
            .setContentText(name)
            .setVibrate(longArrayOf(500, 500, 1000, 1000, 500))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Schedule Notification Channel"
            val descriptionText = "This type of notification is sent to notify Todo alert"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).
            apply {
                description = descriptionText
            }
            // Register the channel with the system

            manager.createNotificationChannel(channel)
        }
        manager.notify(1, builder.build())
    }
    fun makeStatusNotification(message: String, context: Context) {
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = "Schedule Notification Channel"
            val CHANNEL_ID = "my_channel"
            val description = "Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }

        // Create the notification
        val CHANNEL_ID = "my_channel"
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.downloading)
            .setContentTitle("NEWS")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
        // Show the notification
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }

    /**
     * Returns owned [ViewModelStore]
     *
     * @return a `ViewModelStore`
     */
    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }
}