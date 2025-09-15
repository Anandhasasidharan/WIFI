package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifimonitor.model.Rule
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.repository.RuleRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for rules management screen.
 * Handles CRUD operations for parental control rules.
 */
class RulesViewModel : BaseViewModel() {

    private val ruleRepository = RuleRepository()
    private val authRepository = AuthRepository()

    val rules: Flow<List<Rule>> = ruleRepository.getLocalRules()

    fun addRule(rule: Rule) {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            currentUser?.let { user ->
                // Assume parentId is user.uid, childId is selected child
                // For simplicity, using placeholder
                ruleRepository.addRule("parentId", "childId", rule)
            }
        }
    }

    fun deleteRule(rule: Rule) {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            currentUser?.let { user ->
                ruleRepository.deleteRule("parentId", "childId", rule.id)
            }
        }
    }
}