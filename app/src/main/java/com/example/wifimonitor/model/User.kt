package com.example.wifimonitor.model

import com.google.firebase.Timestamp

/**
 * Represents a user in the system.
 * @param uid Firebase user ID
 * @param email User's email address
 * @param role Either "parent" or "child"
 */
data class User(
    val uid: String = "",
    val email: String = "",
    val role: String = "parent"
)