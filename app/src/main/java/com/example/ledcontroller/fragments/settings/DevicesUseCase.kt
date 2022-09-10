package com.example.ledcontroller.fragments.settings

import com.example.ledcontroller.fragments.settings.data.Device
import com.example.ledcontroller.repository.DeviceRepository

class DevicesUseCase(private val deviceRepository: DeviceRepository) {
    fun findDevices(): List<Device> {
        return deviceRepository.findDevices()
    }

    fun connect(address: String): Boolean {
        return deviceRepository.connect(address)
    }
}