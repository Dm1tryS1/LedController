package com.example.smarthome.repository

import com.example.smarthome.common.getTime
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.mapper.*
import com.example.smarthome.service.network.model.*

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>,
        systemIp: String,
        callback: (success: Boolean) -> Unit
    ) {
        try {
            networkModule.createConfigService(systemIp).sendConfig(wifiDevicesItem)
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
            getAllResponseMapper(response, getTime())
        } catch (e: Exception) {
            emptyList()
        }

    suspend fun getTemperature(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).temperature()
            temperatureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getPressure(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).pressure()
            pressureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getHumidity(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).humidity()
            humidityResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun condCommand(
        systemIp: String,
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService(systemIp).condcommand(ConditionerRequest(command))
            conditionerResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun humCommand(
        systemIp: String,
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService(systemIp).humcommand(HumidifierRequest(command))
            humidifierResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun setTimer(
        systemIp: String,
        value: Int
    ) =
        try {
            networkModule.createConfigService(systemIp).systemtimer(SystemTimerRequest(value))
        } catch (e: Exception) {
            null
        }

    suspend fun setSystemSettings(
        systemIp: String,
        minTemp: Int,
        maxTemp: Int,
        minHum: Int,
        maxHum: Int,
        displayedValue: Int
    ) =
        try {
            networkModule.createConfigService(systemIp).systemsettings(
                SystemSettingsRequest(
                    minHum = minHum,
                    maxHum = maxHum,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    displayedValue = displayedValue
                )
            )
        } catch (e: Exception) {
            null
        }

}