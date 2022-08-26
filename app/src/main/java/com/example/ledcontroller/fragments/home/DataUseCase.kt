package com.example.ledcontroller.fragments.home

import com.example.ledcontroller.fragments.settings.DeviceRepository

class DataUseCase(private val deviceRepository: DeviceRepository) {
    fun sendData(data: String): Boolean {
        return deviceRepository.sendData(data)
    }
}