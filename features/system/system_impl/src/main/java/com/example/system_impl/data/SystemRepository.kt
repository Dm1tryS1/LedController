package com.example.system_impl.data

import com.example.network.NetworkFactory
import com.example.system_impl.data.api.SystemService
import com.example.system_impl.data.model.SystemSettingsRequest

class SystemRepository(
    networkModule: NetworkFactory,
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