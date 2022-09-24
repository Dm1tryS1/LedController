package com.example.ledcontroller.fragments.settings

import com.example.ledcontroller.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.ledcontroller.repository.DeviceRepository
import com.example.ledcontroller.repository.Storage

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