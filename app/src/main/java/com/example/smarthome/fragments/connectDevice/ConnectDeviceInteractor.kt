package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.repository.Storage
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.smarthome.repository.model.WifiInfo
import kotlinx.coroutines.flow.callbackFlow

class ConnectDeviceInteractor(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val storage: Storage
) {

    fun checkDeviceConnection() = wifiDeviceRepository.checkDeviceConnection()

    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connect(wifiInfo: WifiInfo,callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveIdConnectedDevice(id: Int) = storage.saveInt(Storage.idOfConditioener, id)

    fun saveIpConnectedDevice(ip: String) = storage.saveString(Storage.ipOfConditioener, ip)
}