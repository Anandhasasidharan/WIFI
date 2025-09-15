package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifimonitor.model.UsageLog
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.repository.UsageLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for usage logs screen.
 * Manages display of historical usage data.
 */
class UsageLogsViewModel : BaseViewModel() {

    private val usageLogRepository = UsageLogRepository()
    private val authRepository = AuthRepository()

    val usageLogs: Flow<List<UsageLog>> = usageLogRepository.getLocalUsageLogs()

    fun refreshLogs() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            currentUser?.let { user ->
                usageLogRepository.syncLogs("parentId", user.uid)
            }
        }
    }
}