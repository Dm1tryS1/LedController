package com.example.SmartHome.fragments.settings

import com.example.SmartHome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.SmartHome.repository.DeviceRepository
import com.example.SmartHome.repository.Storage

class DevicesUseCase(private val deviceRepository: DeviceRepository, private val storage: Storage) {
    fun findDevices(): List<DeviceViewItem> {
        return deviceRepository.findDevices()
    }

    fun connect(address: String, callback: (result: Boolean) -> Unit) {
        storage.getUserSettings {
            callback(deviceRepository.connect(address, it))
        }
    }
}