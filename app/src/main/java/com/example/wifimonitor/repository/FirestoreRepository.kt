package com.example.wifimonitor.repository

import com.example.wifimonitor.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repository handling Firestore database operations.
 * Manages CRUD operations for parents, children, rules, usage logs, and analysis reports.
 */
class FirestoreRepository : BaseRepository() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Collection references
    private val parentsCollection = firestore.collection("parents")
    private val childrenCollection = firestore.collection("children")
    private val rulesCollection = firestore.collection("rules")
    private val usageLogsCollection = firestore.collection("usageLogs")
    private val analysisReportsCollection = firestore.collection("analysisReports")

    // Parent operations
    suspend fun createParentProfile(parentId: String, email: String) {
        val parentData = hashMapOf("email" to email)
        parentsCollection.document(parentId).set(parentData).await()
    }

    // Child operations
    suspend fun addChild(parentId: String, child: Child) {
        val childRef = parentsCollection.document(parentId)
            .collection("children").document(child.id)
        childRef.set(child).await()
    }

    fun getChildren(parentId: String): Flow<List<Child>> = callbackFlow {
        val listener = parentsCollection.document(parentId)
            .collection("children")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val children = snapshot?.documents?.map { doc ->
                    doc.toObject(Child::class.java)?.copy(id = doc.id) ?: Child()
                } ?: emptyList()
                trySend(children)
            }
        awaitClose { listener.remove() }
    }

    // Rule operations
    suspend fun addRule(parentId: String, childId: String, rule: Rule) {
        val ruleRef = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("rules").document(rule.id)
        ruleRef.set(rule).await()
    }

    fun getRules(parentId: String, childId: String): Flow<List<Rule>> = callbackFlow {
        val listener = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("rules")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val rules = snapshot?.documents?.map { doc ->
                    doc.toObject(Rule::class.java)?.copy(id = doc.id) ?: Rule()
                } ?: emptyList()
                trySend(rules)
            }
        awaitClose { listener.remove() }
    }

    suspend fun deleteRule(parentId: String, childId: String, ruleId: String) {
        parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("rules").document(ruleId).delete().await()
    }

    // Usage log operations
    suspend fun addUsageLog(parentId: String, childId: String, log: UsageLog) {
        val logRef = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("usageLogs").document(log.id)
        logRef.set(log).await()
    }

    fun getUsageLogs(parentId: String, childId: String): Flow<List<UsageLog>> = callbackFlow {
        val listener = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("usageLogs")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val logs = snapshot?.documents?.map { doc ->
                    doc.toObject(UsageLog::class.java)?.copy(id = doc.id) ?: UsageLog()
                } ?: emptyList()
                trySend(logs)
            }
        awaitClose { listener.remove() }
    }

    // Analysis report operations
    suspend fun addAnalysisReport(parentId: String, childId: String, report: AnalysisReport) {
        val reportRef = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("analysisReports").document(report.id)
        reportRef.set(report).await()
    }

    fun getAnalysisReports(parentId: String, childId: String): Flow<List<AnalysisReport>> = callbackFlow {
        val listener = parentsCollection.document(parentId)
            .collection("children").document(childId)
            .collection("analysisReports")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val reports = snapshot?.documents?.map { doc ->
                    doc.toObject(AnalysisReport::class.java)?.copy(id = doc.id) ?: AnalysisReport()
                } ?: emptyList()
                trySend(reports)
            }
        awaitClose { listener.remove() }
    }
}