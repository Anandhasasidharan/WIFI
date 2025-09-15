package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Base ViewModel class providing common functionality for all ViewModels in the app.
 * Extends androidx.lifecycle.ViewModel to handle lifecycle-aware data.
 */
abstract class BaseViewModel : ViewModel() {

    // Override onCleared to perform cleanup if needed
    override fun onCleared() {
        super.onCleared()
        // Cleanup resources here
    }
}