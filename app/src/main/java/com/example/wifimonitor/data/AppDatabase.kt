package com.example.wifimonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room database for local caching of usage logs and rules.
 * Provides access to DAOs for data operations.
 */
@Database(
    entities = [LocalUsageLog::class, LocalRule::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usageLogDao(): UsageLogDao

    abstract fun ruleDao(): RuleDao
}