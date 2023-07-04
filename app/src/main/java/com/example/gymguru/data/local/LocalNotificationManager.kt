package com.example.gymguru.data.local

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.gymguru.R
import com.example.gymguru.data.model.NotificationData
import com.example.gymguru.presentation.main.MainActivity
import java.util.Calendar
import javax.inject.Inject

class LocalNotificationManager @Inject constructor(
    private val notificationManager: NotificationManager,
    private val context: Context
) {

    private val notificationAction by lazy {
        PendingIntent.getActivity(
            context,
            NOTIFICATION_ACTION_REQUEST_CODE,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun showNotification() {
        createNotificationChannel()

        val notification = context.getNotificationCompat(
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID,
            NotificationData(
                title = context.getString(R.string.notification_title),
                body = context.getString(R.string.notification_body)
            )
        )

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun Context.getNotificationCompat(
        notificationID: Int,
        channelId: String,
        notificationData: NotificationData
    ) = with(notificationData) {
        NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setShowWhen(true)
            .setSmallIcon(R.drawable.notification_icon)
            .setWhen(Calendar.getInstance().timeInMillis)
            .setColor(applicationContext.getColor(R.color.primary))
            .setColorized(true)
            .setContentIntent(notificationAction)
            .setAutoCancel(true)
            .build()
    }

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_WORKOUT_REMINDER"
        const val NOTIFICATION_CHANNEL_NAME = "Workout reminder"
        const val NOTIFICATION_ACTION_REQUEST_CODE = 1000
        const val NOTIFICATION_ID = 2137
    }
}
