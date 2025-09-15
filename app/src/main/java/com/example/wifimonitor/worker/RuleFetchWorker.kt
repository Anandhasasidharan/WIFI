package com.example.wifimonitor.worker

import android.content.Context
import androidx.work.*
import com.example.wifimonitor.data.LocalRule
import com.example.wifimonitor.data.RuleDao
import com.example.wifimonitor.model.Rule
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.repository.FirestoreRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.first

/**
 * WorkManager worker that fetches latest rules from Firestore.
 * Updates local cache with current parental control rules.
 */
class RuleFetchWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val ruleDao: RuleDao? = null // TODO: Initialize with Room
    private val firestoreRepository = FirestoreRepository()
    private val authRepository = AuthRepository()

    companion object {
        const val WORK_NAME = "rule_fetch_work"
    }

    override suspend fun doWork(): Result {
        return try {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null) {
                return Result.failure()
            }

            // Assume parentId is known, fetch rules for this child
            val parentId = "parentId" // Should be stored or retrieved
            val rules = firestoreRepository.getRules(parentId, currentUser.uid).first()

            // Convert to local entities
            val localRules = rules.map { rule ->
                LocalRule(
                    id = rule.id,
                    type = rule.type,
                    value = rule.value,
                    activeFrom = rule.activeFrom.seconds * 1000,
                    activeTo = rule.activeTo.seconds * 1000,
                    synced = true
                )
            }

            // TODO: Implement with Room
            // Update local cache
            // ruleDao.insertAll(localRules)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}