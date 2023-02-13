package com.example.smarthome.fragments.settings

import com.example.smarthome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.Storage

class DevicesUseCase(private val deviceRepository: DeviceRepository, private val storage: Storage) {
    fun findDevices(): List<DeviceViewItem>? {
        return deviceRepository.findDevices()
    }

    suspend fun connect(address: String, callback: (result: Boolean) -> Unit) {
        var timer: Int? = storage.getUserSettings(Storage.userTimer)
        var maxHum: Int? = storage.getUserSettings(Storage.userMaxHumidity)
        var minHum: Int? = storage.getUserSettings(Storage.userMinHumidity)
        var maxTemp: Int? = storage.getUserSettings(Storage.userMaxTemperature)
        var minTemp: Int? = storage.getUserSettings(Storage.userMinTemperature)
        var displayedValue: Int? = storage.getUserSettings(Storage.userDisplayedValue)

        if (timer == -1) timer = null
        if (maxHum == -1) maxHum = null
        if (minHum == -1) minHum = null
        if (maxTemp == -1) maxTemp = null
        if (minTemp == -1) minTemp = null
        if (displayedValue == -1) displayedValue = null

        callback(
            deviceRepository.connect(
                address,
                timer = timer,
                maxTemp = maxTemp,
                minTemp = minTemp,
                maxHum = maxHum,
                minHum = minHum,
                displayedValue = displayedValue
            )
        )
    }

    fun disconnect(): Boolean {
        return deviceRepository.disconnect()
    }
}