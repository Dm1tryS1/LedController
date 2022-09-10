package com.example.ledcontroller.fragments.information

import com.example.ledcontroller.repository.DeviceRepository

class GetInfoUseCase(private val deviceRepository: DeviceRepository) {
    fun getInfo() {
        deviceRepository.getInfo()
    }
}