package com.example.wifimonitor.model

/**
 * Data class representing domain classification result from Gemini API.
 */
data class DomainCategory(
    val domain: String,
    val category: String,
    val confidence: Float
)