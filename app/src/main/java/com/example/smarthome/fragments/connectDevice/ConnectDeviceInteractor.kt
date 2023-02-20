package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.common.device.SensorType
import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.Storage
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.service.network.mapper.sendConfigRequestMapper

class ConnectDeviceInteractor(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository,
    private val networkRepository: NetworkRepository,
    private val storage: Storage
) {
    fun getJSONfromFile(path: String) = fileRepository.getJSONfromFile(path)
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveConnectedDevice(id: Int, type: Int, ip: String) {
        when (type) {
            SensorType.Conditioner.type -> {
                storage.saveString(Storage.ipOfConditioener, ip)
                storage.saveInt(Storage.idOfConditioener, id)
            }
            SensorType.Humidifier.type -> {
                storage.saveString(Storage.ipOfHumidifier, ip)
                storage.saveInt(Storage.ipOfHumidifier, id)
            }
        }
    }

    fun getSystemIp() = storage.getString(Storage.systemIp)

    suspend fun sendConfig(
        systemIp: String,
        data: List<Pair<String, Int>>,
        callback: (success: Boolean) -> Unit
    ) {
        networkRepository.sendConfig(
            fileRepository.findDeviceConfig(data.map { it.second }).mapNotNull { item ->
                val ip = data.find { it.second == item.id }?.first
                if (!ip.isNullOrEmpty()) {
                    sendConfigRequestMapper(item, ip)
                } else {
                    null
                }
            },
            systemIp
        ) {
            callback(it)
        }
    }

}