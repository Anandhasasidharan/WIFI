package com.example.wifimonitor.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.wifimonitor.R
import com.example.wifimonitor.ui.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper class for creating and showing notifications.
 * Handles different types of parental control alerts.
 */
@Singleton
class NotificationHelper @Inject constructor(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_RULE_VIOLATION,
                    "Rule Violations",
                    NotificationManager.IMPORTANCE_HIGH
                ),
                NotificationChannel(
                    CHANNEL_NEW_DEVICE,
                    "New Devices",
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                NotificationChannel(
                    CHANNEL_USAGE_THRESHOLD,
                    "Usage Thresholds",
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                NotificationChannel(
                    CHANNEL_GENERAL,
                    "General",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }

    fun showRuleViolationNotification(title: String, body: String) {
        showNotification(CHANNEL_RULE_VIOLATION, title, body, 1)
    }

    fun showNewDeviceNotification(title: String, body: String) {
        showNotification(CHANNEL_NEW_DEVICE, title, body, 2)
    }

    fun showUsageThresholdNotification(title: String, body: String) {
        showNotification(CHANNEL_USAGE_THRESHOLD, title, body, 3)
    }

    fun showGeneralNotification(title: String, body: String) {
        showNotification(CHANNEL_GENERAL, title, body, 4)
    }

    private fun showNotification(channelId: String, title: String, body: String, notificationId: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Use system icon
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val CHANNEL_RULE_VIOLATION = "rule_violation"
        const val CHANNEL_NEW_DEVICE = "new_device"
        const val CHANNEL_USAGE_THRESHOLD = "usage_threshold"
        const val CHANNEL_GENERAL = "general"
    }
}