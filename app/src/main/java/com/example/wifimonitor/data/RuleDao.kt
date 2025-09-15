package com.example.wifimonitor.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for local rules.
 * Provides methods to manage cached parental control rules.
 */
@Dao
interface RuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: LocalRule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rules: List<LocalRule>)

    @Query("SELECT * FROM rules WHERE synced = 0")
    suspend fun getUnsyncedRules(): List<LocalRule>

    @Query("SELECT * FROM rules ORDER BY activeFrom DESC")
    fun getAllRules(): Flow<List<LocalRule>>

    @Query("DELETE FROM rules WHERE id = :ruleId")
    suspend fun deleteRule(ruleId: String)

    @Update
    suspend fun updateRule(rule: LocalRule)
}