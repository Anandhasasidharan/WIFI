package com.example.wifimonitor.utils

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for monitoring application usage statistics.
 * Uses UsageStatsManager to collect screen time and app usage data.
 */
@Singleton
class AppUsageMonitor @Inject constructor(
    private val context: Context,
    private val usageStatsManager: UsageStatsManager
) {

    /**
     * Get usage statistics for all apps over a time period
     */
    fun getAppUsageStats(startTime: Long, endTime: Long): Map<String, Long> {
        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return usageStats.associate { stats ->
            stats.packageName to stats.totalTimeInForeground
        }
    }

    /**
     * Get usage time for a specific package
     */
    fun getUsageTimeForPackage(packageName: String, startTime: Long, endTime: Long): Long {
        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return usageStats.find { it.packageName == packageName }?.totalTimeInForeground ?: 0L
    }

    /**
     * Check if usage access permission is granted
     */
    fun isUsageAccessGranted(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        } else {
            appOps.checkOpNoThrow(
                android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        }
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }
}