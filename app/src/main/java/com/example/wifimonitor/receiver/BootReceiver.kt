package com.example.wifimonitor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wifimonitor.service.MonitoringService

/**
 * Broadcast receiver that starts monitoring service on device boot.
 * Ensures parental controls are active after device restart.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start monitoring service
            val serviceIntent = Intent(context, MonitoringService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}