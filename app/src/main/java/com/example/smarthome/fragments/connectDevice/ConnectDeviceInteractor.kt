package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.repository.WifiDeviceRepository

class ConnectDeviceInteractor(private val wifiDeviceRepository: WifiDeviceRepository) {

    fun checkDeviceConnection() = wifiDeviceRepository.checkDeviceConnection()

    fun connect(ssid: String, password: String) = wifiDeviceRepository.connect(ssid, password)
}