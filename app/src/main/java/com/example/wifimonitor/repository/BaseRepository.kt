package com.example.wifimonitor.repository

/**
 * Base repository class providing common functionality for data access.
 * Handles common patterns like error handling and data transformation.
 */
abstract class BaseRepository {

    // Common repository methods can be added here
    protected fun handleError(throwable: Throwable) {
        // Log error or handle it
    }
}