package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.repository.Storage
import com.example.smarthome.repository.WifiDeviceRepository

class ConnectDeviceInteractor(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val storage: Storage
) {

    fun checkDeviceConnection() = wifiDeviceRepository.checkDeviceConnection()

    fun connect(ssid: String, password: String) = wifiDeviceRepository.connect(ssid, password)

    fun saveIdConnectedDevice(id: Int) = storage.saveInt(Storage.idOfConditioener, id)

    fun saveIpConnectedDevice(ip: String) = storage.saveString(Storage.ipOfConditioener, ip)
}