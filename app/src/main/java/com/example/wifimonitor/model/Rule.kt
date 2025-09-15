package com.example.wifimonitor.model

import com.google.firebase.Timestamp

/**
 * Enum representing different types of parental control rules.
 */
enum class RuleType {
    BLOCK_DOMAIN,
    TIME_LIMIT,
    BEDTIME
}

/**
 * Represents a parental control rule.
 * @param id Unique identifier for the rule
 * @param type Type of rule (block domain, time limit, bedtime)
 * @param value Domain name, package name, or time value depending on type
 * @param activeFrom Start time for the rule
 * @param activeTo End time for the rule
 */
data class Rule(
    val id: String = "",
    val type: RuleType = RuleType.BLOCK_DOMAIN,
    val value: String = "",
    val activeFrom: Timestamp = Timestamp.now(),
    val activeTo: Timestamp = Timestamp.now()
)