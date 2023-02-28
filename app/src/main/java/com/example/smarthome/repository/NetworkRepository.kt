package com.example.smarthome.repository

import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.mapper.getAllResponseMapper
import com.example.smarthome.service.network.mapper.temperatureResponseMapper
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

    suspend fun getInfo(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).getAll()
            getAllResponseMapper(response)
        } catch (e: Exception) {
            emptyList()
        }


    suspend fun getTemperature(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).temperature()
            temperatureResponseMapper(response)
        } catch (e: Exception) {
            null
        }

}