package com.example.wifimonitor.model

import com.google.firebase.Timestamp

/**
 * Represents a child device registered by a parent.
 * @param id Unique identifier for the child
 * @param name Display name for the child
 * @param deviceId Unique device identifier
 * @param createdAt Timestamp when the child was registered
 */
data class Child(
    val id: String = "",
    val name: String = "",
    val deviceId: String = "",
    val createdAt: Timestamp = Timestamp.now()
)