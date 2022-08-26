package com.example.ledcontroller.fragments.settings

import com.example.ledcontroller.fragments.settings.DeviceRepository

class DevicesUseCase(private val deviceRepository: DeviceRepository) {
    fun findDevices(): MutableMap<String, String> {
        return deviceRepository.findDevices()
    }

    fun connect(address: String): Boolean {
        return deviceRepository.connect(address)
    }
}