package com.example.wifimonitor.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Firebase Cloud Messaging service for handling push notifications.
 * Receives alerts about rule violations, new device connections, and usage thresholds.
 */
class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Handle data payload
        remoteMessage.data.let { data ->
            val type = data["type"]
            val title = data["title"] ?: "Wi-Fi Monitor Alert"
            val body = data["body"] ?: "New notification"

            when (type) {
                "rule_violation" -> handleRuleViolation(title, body)
                "new_device" -> handleNewDevice(title, body)
                "usage_threshold" -> handleUsageThreshold(title, body)
                else -> showGeneralNotification(title, body)
            }
        }

        // Handle notification payload (if present)
        remoteMessage.notification?.let { notification ->
            showGeneralNotification(
                notification.title ?: "Wi-Fi Monitor",
                notification.body ?: "New notification"
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to server for targeted notifications
        sendRegistrationToServer(token)
    }

    private fun handleRuleViolation(title: String, body: String) {
        // TODO: Show rule violation notification
    }

    private fun handleNewDevice(title: String, body: String) {
        // TODO: Show new device notification
    }

    private fun handleUsageThreshold(title: String, body: String) {
        // TODO: Show usage threshold notification
    }

    private fun showGeneralNotification(title: String, body: String) {
        // TODO: Show general notification
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Send token to your server
    }
}