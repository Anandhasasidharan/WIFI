package com.example.wifimonitor.repository

import com.example.wifimonitor.data.LocalRule
import com.example.wifimonitor.data.RuleDao
import com.example.wifimonitor.model.Rule
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

/**
 * Repository for managing parental control rules.
 * Handles both local caching and Firestore synchronization.
 */
class RuleRepository : BaseRepository() {

    private val ruleDao: RuleDao? = null // TODO: Initialize with Room
    private val firestoreRepository = FirestoreRepository()

    /**
     * Get all rules from local cache
     */
    fun getLocalRules(): Flow<List<Rule>> {
        // TODO: Implement with Room
        return emptyFlow()
    }

    /**
     * Add a new rule locally and sync to Firestore
     */
    suspend fun addRule(parentId: String, childId: String, rule: Rule) {
        // Save to Firestore first
        firestoreRepository.addRule(parentId, childId, rule)

        // Cache locally
        val localRule = LocalRule(
            id = rule.id,
            type = rule.type,
            value = rule.value,
            activeFrom = rule.activeFrom.seconds * 1000,
            activeTo = rule.activeTo.seconds * 1000,
            synced = true
        )
        // TODO: Implement with Room
        // ruleDao.insert(localRule)
    }

    /**
     * Delete a rule
     */
    suspend fun deleteRule(parentId: String, childId: String, ruleId: String) {
        firestoreRepository.deleteRule(parentId, childId, ruleId)
        // TODO: Implement with Room
        // ruleDao.deleteRule(ruleId)
    }

    /**
     * Extension function to convert LocalRule to Rule
     */
    private fun LocalRule.toRule(): Rule {
        return Rule(
            id = id,
            type = type,
            value = value,
            activeFrom = Timestamp(activeFrom / 1000, 0),
            activeTo = Timestamp(activeTo / 1000, 0)
        )
    }
}