package com.example.wifimonitor.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wifimonitor.model.RuleType
import com.google.firebase.Timestamp

/**
 * Local Room entity for caching rules.
 * Stores parental control rules locally for offline access.
 */
@Entity(tableName = "rules")
data class LocalRule(
    @PrimaryKey val id: String,
    val type: RuleType = RuleType.BLOCK_DOMAIN,
    val value: String = "",
    val activeFrom: Long = System.currentTimeMillis(),
    val activeTo: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)