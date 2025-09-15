package com.example.wifimonitor.utils

import com.example.wifimonitor.model.DomainCategory
import com.example.wifimonitor.model.UsageLog
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Client for interacting with Google Gemini AI API.
 * Handles domain classification and usage analysis generation.
 */
class GeminiApiClient {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyC7TnYkMfslbF9VEsAd5D4Sc8xvDT2BckA"
    )

    private val gson = Gson()

    /**
     * Classify domains from usage logs into categories
     */
    suspend fun classifyDomains(logs: List<UsageLog>): List<DomainCategory> {
        return withContext(Dispatchers.IO) {
            try {
                val domains = logs.map { it.domain }.distinct()
                val prompt = """
                    You are an AI that classifies domain names visited by children into categories.
                    Categories: [adult, social_media, gaming, educational, general].
                    Return JSON array: [{"domain": "example.com", "category": "educational", "confidence": 0.95}]
                    
                    Domains to classify: ${domains.joinToString(", ")}
                """.trimIndent()

                val response = generativeModel.generateContent(prompt)
                val jsonResponse = response.text ?: "[]"

                val type = object : TypeToken<List<DomainCategory>>() {}.type
                gson.fromJson(jsonResponse, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    /**
     * Generate a summary report of child's usage patterns
     */
    suspend fun generateUsageSummary(logs: List<UsageLog>): String {
        return withContext(Dispatchers.IO) {
            try {
                val logsText = logs.joinToString("\n") {
                    "${it.domain}: ${it.duration}s, ${it.dataUsed} bytes"
                }

                val prompt = """
                    You are an AI parental assistant.
                    Summarize this child's usage logs into a markdown report showing risky domains, rising trends, and screen time balance.
                    Return short markdown text.
                    
                    Usage logs:
                    $logsText
                """.trimIndent()

                val response = generativeModel.generateContent(prompt)
                response.text ?: "Unable to generate summary"
            } catch (e: Exception) {
                "Error generating summary: ${e.message}"
            }
        }
    }
}