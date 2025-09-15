package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifimonitor.model.AnalysisReport
import com.example.wifimonitor.repository.AnalysisRepository
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.repository.UsageLogRepository
import com.example.wifimonitor.utils.GeminiApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel for analysis screen.
 * Handles AI-generated reports and insights.
 */
class AnalysisViewModel : BaseViewModel() {

    private val analysisRepository = AnalysisRepository()
    private val usageLogRepository = UsageLogRepository()
    private val geminiApiClient = GeminiApiClient()
    private val authRepository = AuthRepository()

    val analysisReports: Flow<List<AnalysisReport>> = analysisRepository.getAnalysisReports("parentId", "childId")

    fun generateAnalysis() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            currentUser?.let { user ->
                try {
                    // Get recent logs
                    val logs = usageLogRepository.getLocalUsageLogs().first().takeLast(100)

                    // Generate summary using Gemini
                    val categories = geminiApiClient.classifyDomains(logs)
                    val summary = "Analysis generated with ${categories.size} domain categories"

                    // Save report
                    analysisRepository.createAnalysisReport("parentId", user.uid, summary)
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }
    }
}