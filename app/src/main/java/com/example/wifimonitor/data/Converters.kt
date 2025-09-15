package com.example.wifimonitor.data

import androidx.room.TypeConverter
import com.example.wifimonitor.model.RuleType

/**
 * Type converters for Room database.
 * Handles conversion between custom types and database-compatible types.
 */
class Converters {

    @TypeConverter
    fun fromRuleType(type: RuleType): String = type.name

    @TypeConverter
    fun toRuleType(name: String): RuleType = RuleType.valueOf(name)
}