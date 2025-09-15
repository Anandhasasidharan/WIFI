package com.example.wifimonitor.model

import com.google.firebase.Timestamp

/**
 * Represents a usage log entry for monitoring child device activity.
 * @param id Unique identifier for the log entry
 * @param domain Domain name accessed (for web traffic)
 * @param packageName Android package name (for app usage)
 * @param duration Time spent in seconds
 * @param dataUsed Data consumed in bytes
 * @param timestamp When the activity occurred
 */
data class UsageLog(
    val id: String = "",
    val domain: String = "",
    val packageName: String = "",
    val duration: Long = 0L,
    val dataUsed: Long = 0L,
    val timestamp: Timestamp = Timestamp.now()
)