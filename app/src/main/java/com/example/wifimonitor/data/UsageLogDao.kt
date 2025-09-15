package com.example.wifimonitor.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for local usage logs.
 * Provides methods to insert, query, and manage cached usage logs.
 */
@Dao
interface UsageLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: LocalUsageLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(logs: List<LocalUsageLog>)

    @Query("SELECT * FROM usage_logs WHERE synced = 0")
    suspend fun getUnsyncedLogs(): List<LocalUsageLog>

    @Query("SELECT * FROM usage_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<LocalUsageLog>>

    @Query("DELETE FROM usage_logs WHERE synced = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldSyncedLogs(cutoffTime: Long)

    @Update
    suspend fun updateLog(log: LocalUsageLog)
}