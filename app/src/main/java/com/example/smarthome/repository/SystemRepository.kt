package com.example.smarthome.repository

import com.example.network.NetworkFactory
import com.example.smarthome.repository.network.model.SystemSettingsRequest

class SystemRepository(
    networkModule: NetworkFactory,
) {
    private var systemService = networkModule.createService(com.example.smarthome.repository.network.SystemService::class.java)

    suspend fun setSystemSettings(
        minTemp: Int,
        maxTemp: Int,
        minHum: Int,
        maxHum: Int,
        displayedValue: Int
    ) =
        systemService.systemsettings(
            SystemSettingsRequest(
                minHum = minHum,
                maxHum = maxHum,
                minTemp = minTemp,
                maxTemp = maxTemp,
                displayedValue = displayedValue
            )
        )
}