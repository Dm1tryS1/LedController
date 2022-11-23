package com.example.smarthome.fragments.home

import com.example.smarthome.repository.DeviceRepository

class HomeInteractor(val deviceRepository: DeviceRepository) {

    fun checkConnection() = deviceRepository.isConnected
}