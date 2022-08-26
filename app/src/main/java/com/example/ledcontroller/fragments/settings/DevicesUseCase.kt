package com.example.ledcontroller.fragments.settings

import com.example.ledcontroller.data.Device

class DevicesUseCase(private val deviceRepository: DeviceRepository) {
    fun findDevices(): List<Device> {
        return deviceRepository.findDevices()
    }

    fun connect(address: String): Boolean {
        return deviceRepository.connect(address)
    }
}