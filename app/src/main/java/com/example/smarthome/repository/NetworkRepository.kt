package com.example.smarthome.repository

import com.example.smarthome.common.getTime
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.mapper.*
import com.example.smarthome.service.network.model.*

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>
    ) =
        try {
            networkModule.createConfigService().sendConfig(wifiDevicesItem)
            true
        } catch (e: Exception) {
            false
        }


    suspend fun getInfo() =
        try {
            val response = networkModule.createConfigService().getAll()
            getAllResponseMapper(response, getTime())
        } catch (e: Exception) {
            emptyList()
        }

    suspend fun getTemperature(
        id: Int
    ) =
        try {
            val response = networkModule.createConfigService().temperature(id)
            temperatureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getPressure(
        id: Int
    ) =
        try {
            val response = networkModule.createConfigService().pressure(id)
            pressureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getHumidity(
        id: Int
    ) =
        try {
            val response = networkModule.createConfigService().humidity(id)
            humidityResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun condCommand(
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService().condcommand(ConditionerRequest(command))
            conditionerResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun humCommand(
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService().humcommand(HumidifierRequest(command))
            humidifierResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun setTimer(
        value: Int
    ) =
        try {
            networkModule.createConfigService().systemtimer(SystemTimerRequest(value))
        } catch (e: Exception) {
            null
        }

    suspend fun setSystemSettings(
        minTemp: Int,
        maxTemp: Int,
        minHum: Int,
        maxHum: Int,
        displayedValue: Int
    ) =
        try {
            networkModule.createConfigService().systemsettings(
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

    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        try {
            networkModule.createConfigService()
                .irreceiver(IrReceiverRequest(command, deviceType))
        } catch (e: Exception) {
            null
        }

}