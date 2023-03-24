package com.example.smarthome.repository

import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.SystemService
import com.example.smarthome.service.network.model.SystemSettingsRequest

class SystemRepository(
    networkModule: NetworkModule,
) {
    private var systemService = networkModule.createService(SystemService::class.java)

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