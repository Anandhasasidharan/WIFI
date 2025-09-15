package com.example.wifimonitor.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for monitoring Wi-Fi connection information.
 * Provides current SSID, connection state, and network changes.
 */
@Singleton
class WifiMonitor @Inject constructor(
    private val context: Context,
    private val wifiManager: WifiManager,
    private val connectivityManager: ConnectivityManager
) {

    /**
     * Get current Wi-Fi SSID
     */
    fun getCurrentSSID(): String? {
        return if (isWifiConnected()) {
            val wifiInfo = wifiManager.connectionInfo
            wifiInfo?.ssid?.removeSurrounding("\"")
        } else {
            null
        }
    }

    /**
     * Check if device is connected to Wi-Fi
     */
    fun isWifiConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    /**
     * Get detailed Wi-Fi information
     */
    fun getWifiInfo(): WifiInfo? {
        return if (isWifiConnected()) {
            wifiManager.connectionInfo
        } else {
            null
        }
    }

    /**
     * Get current network type (Wi-Fi, Mobile, etc.)
     */
    fun getNetworkType(): String {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Wi-Fi"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Mobile"
            else -> "Unknown"
        }
    }
}