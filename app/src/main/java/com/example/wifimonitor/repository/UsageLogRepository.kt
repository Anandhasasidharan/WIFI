package com.example.wifimonitor.repository

import com.example.wifimonitor.data.LocalUsageLog
import com.example.wifimonitor.data.UsageLogDao
import com.example.wifimonitor.model.UsageLog
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * Repository for managing usage logs.
 * Handles local caching and synchronization with Firestore.
 */
class UsageLogRepository : BaseRepository() {

    private val usageLogDao: UsageLogDao? = null // TODO: Initialize with Room
    private val firestoreRepository = FirestoreRepository()

    /**
     * Get all usage logs from local cache
     */
    fun getLocalUsageLogs(): Flow<List<UsageLog>> {
        // TODO: Implement with Room
        return flowOf(emptyList())
    }

    /**
     * Add a usage log locally (will be synced later)
     */
    suspend fun addUsageLog(log: UsageLog) {
        // TODO: Implement with Room
        // val localLog = LocalUsageLog(...)
        // usageLogDao.insert(localLog)
    }

    /**
     * Sync unsynced logs to Firestore
     */
    suspend fun syncLogs(parentId: String, childId: String) {
        // TODO: Implement with Room
        // val unsyncedLogs = usageLogDao.getUnsyncedLogs()
        // for (localLog in unsyncedLogs) {
        //     firestoreRepository.addUsageLog(parentId, childId, localLog.toUsageLog())
        //     usageLogDao.updateLog(localLog.copy(synced = true))
        // }
    }

    /**
     * Extension function to convert LocalUsageLog to UsageLog
     */
    private fun LocalUsageLog.toUsageLog(): UsageLog {
        return UsageLog(
            id = id,
            domain = domain,
            packageName = packageName,
            duration = duration,
            dataUsed = dataUsed,
            timestamp = Timestamp(timestamp / 1000, 0)
        )
    }
}