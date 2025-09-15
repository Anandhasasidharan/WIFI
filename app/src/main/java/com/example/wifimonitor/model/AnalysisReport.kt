package com.example.wifimonitor.model

import com.google.firebase.Timestamp

/**
 * Represents an AI-generated analysis report of child's usage patterns.
 * @param id Unique identifier for the report
 * @param summary Markdown-formatted summary of usage analysis
 * @param createdAt When the report was generated
 */
data class AnalysisReport(
    val id: String = "",
    val summary: String = "",
    val createdAt: Timestamp = Timestamp.now()
)