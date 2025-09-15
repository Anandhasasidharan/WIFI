package com.example.wifimonitor.repository

import com.example.wifimonitor.model.AnalysisReport
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Repository for managing AI-generated analysis reports.
 * Handles creation and retrieval of parental insights.
 */
class AnalysisRepository : BaseRepository() {

    private val firestoreRepository = FirestoreRepository()

    /**
     * Get analysis reports for a child
     */
    fun getAnalysisReports(parentId: String, childId: String): Flow<List<AnalysisReport>> {
        return firestoreRepository.getAnalysisReports(parentId, childId)
    }

    /**
     * Create and save a new analysis report
     */
    suspend fun createAnalysisReport(parentId: String, childId: String, summary: String) {
        val report = AnalysisReport(
            id = UUID.randomUUID().toString(),
            summary = summary,
            createdAt = Timestamp.now()
        )
        firestoreRepository.addAnalysisReport(parentId, childId, report)
    }
}