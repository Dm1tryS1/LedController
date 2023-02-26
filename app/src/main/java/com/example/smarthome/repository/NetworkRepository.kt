package com.example.smarthome.repository

import android.util.Log
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.model.SendConfigRequest

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>,
        systemIp: String,
        callback: (success: Boolean) -> Unit
    ) {
        try {
            val response = networkModule.createConfigService(systemIp).sendConfig(wifiDevicesItem)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }
}