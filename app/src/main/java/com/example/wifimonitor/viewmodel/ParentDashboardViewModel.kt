package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifimonitor.model.Child
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for the parent dashboard screen.
 * Manages children data and selected child state.
 */
class ParentDashboardViewModel : BaseViewModel() {

    private val authRepository = AuthRepository()
    private val firestoreRepository = FirestoreRepository()

    private val _children = MutableStateFlow<List<Child>>(emptyList())
    val children: StateFlow<List<Child>> = _children

    private val _selectedChild = MutableStateFlow<Child?>(null)
    val selectedChild: StateFlow<Child?> = _selectedChild

    init {
        loadChildren()
    }

    private fun loadChildren() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            currentUser?.let { user ->
                firestoreRepository.getChildren(user.uid).collect { childrenList ->
                    _children.value = childrenList
                    // Auto-select first child if none selected
                    if (_selectedChild.value == null && childrenList.isNotEmpty()) {
                        _selectedChild.value = childrenList.first()
                    }
                }
            }
        }
    }

    fun selectChild(child: Child) {
        _selectedChild.value = child
    }
}