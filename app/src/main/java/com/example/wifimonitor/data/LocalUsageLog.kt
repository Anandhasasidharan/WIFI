package com.example.wifimonitor.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

/**
 * Local Room entity for caching usage logs before syncing to Firestore.
 * Mirrors the UsageLog model but optimized for local storage.
 */
@Entity(tableName = "usage_logs")
data class LocalUsageLog(
    @PrimaryKey val id: String,
    val domain: String = "",
    val packageName: String = "",
    val duration: Long = 0L,
    val dataUsed: Long = 0L,
    val timestamp: Long = System.currentTimeMillis(), // Store as Long for Room
    val synced: Boolean = false // Track sync status
)