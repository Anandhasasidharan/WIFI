package com.example.wifimonitor.utils

import com.example.wifimonitor.model.Rule
import com.example.wifimonitor.model.RuleType
import com.google.firebase.Timestamp
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for enforcing parental control rules.
 * Checks current time and conditions against active rules.
 */
@Singleton
class RuleEnforcer @Inject constructor() {

    /**
     * Check if a domain should be blocked based on current rules
     */
    fun shouldBlockDomain(domain: String, rules: List<Rule>): Boolean {
        val now = Timestamp.now()
        return rules.any { rule ->
            rule.type == RuleType.BLOCK_DOMAIN &&
            rule.value == domain &&
            isRuleActive(rule, now)
        }
    }

    /**
     * Check if app usage should be limited
     */
    fun shouldLimitApp(packageName: String, rules: List<Rule>): Rule? {
        val now = Timestamp.now()
        return rules.find { rule ->
            (rule.type == RuleType.TIME_LIMIT || rule.type == RuleType.BEDTIME) &&
            rule.value == packageName &&
            isRuleActive(rule, now)
        }
    }

    /**
     * Check if current time is within bedtime hours
     */
    fun isBedtime(rules: List<Rule>): Boolean {
        val now = Timestamp.now()
        return rules.any { rule ->
            rule.type == RuleType.BEDTIME && isRuleActive(rule, now)
        }
    }

    /**
     * Check if a rule is currently active
     */
    private fun isRuleActive(rule: Rule, currentTime: Timestamp): Boolean {
        return currentTime.seconds >= rule.activeFrom.seconds &&
               currentTime.seconds <= rule.activeTo.seconds
    }

    /**
     * Get remaining time for time limit rules
     */
    fun getRemainingTime(rule: Rule, usedTime: Long): Long {
        // Assume rule.value contains time limit in minutes
        val limitMinutes = rule.value.toLongOrNull() ?: 0L
        val limitMillis = limitMinutes * 60 * 1000
        return maxOf(0L, limitMillis - usedTime)
    }
}