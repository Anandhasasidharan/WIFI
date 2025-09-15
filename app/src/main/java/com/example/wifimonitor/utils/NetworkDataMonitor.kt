package com.example.wifimonitor.utils

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for monitoring network data usage per application.
 * Uses NetworkStatsManager to collect data consumption statistics.
 */
@Singleton
class NetworkDataMonitor @Inject constructor(
    private val context: Context,
    private val networkStatsManager: NetworkStatsManager
) {

    /**
     * Get data usage for a specific package over a time period
     */
    fun getDataUsageForPackage(packageName: String, startTime: Long, endTime: Long): Long {
        return try {
            val bucket = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI,
                    null,
                    startTime,
                    endTime,
                    getUidForPackage(packageName)
                )
            } else {
                // For older versions, use querySummary
                networkStatsManager.querySummary(
                    ConnectivityManager.TYPE_WIFI,
                    null,
                    startTime,
                    endTime
                )
            }

            var totalBytes = 0L
            while (bucket.hasNextBucket()) {
                val bucketItem = NetworkStats.Bucket()
                bucket.getNextBucket(bucketItem)
                if (bucketItem.uid == getUidForPackage(packageName)) {
                    totalBytes += bucketItem.rxBytes + bucketItem.txBytes
                }
            }
            bucket.close()
            totalBytes
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Get UID for package name
     */
    private fun getUidForPackage(packageName: String): Int {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0).uid
        } catch (e: Exception) {
            -1
        }
    }
}