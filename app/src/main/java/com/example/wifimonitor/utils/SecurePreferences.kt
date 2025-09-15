package com.example.wifimonitor.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure storage utility using EncryptedSharedPreferences.
 * Stores sensitive data like parent PIN and authentication tokens.
 */
@Singleton
class SecurePreferences @Inject constructor(
    private val context: Context
) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Store parent PIN
     */
    fun setParentPin(pin: String) {
        sharedPreferences.edit().putString("parent_pin", pin).apply()
    }

    /**
     * Get parent PIN
     */
    fun getParentPin(): String? {
        return sharedPreferences.getString("parent_pin", null)
    }

    /**
     * Store device role (parent/child)
     */
    fun setDeviceRole(role: String) {
        sharedPreferences.edit().putString("device_role", role).apply()
    }

    /**
     * Get device role
     */
    fun getDeviceRole(): String? {
        return sharedPreferences.getString("device_role", null)
    }

    /**
     * Clear all secure data
     */
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}