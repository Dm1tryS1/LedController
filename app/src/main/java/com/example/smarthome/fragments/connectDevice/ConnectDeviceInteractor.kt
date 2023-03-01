package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.common.device.SensorType
import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.service.network.mapper.sendConfigRequestMapper

class ConnectDeviceInteractor(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository,
    private val networkRepository: NetworkRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    fun getJSONfromFile(path: String) = fileRepository.getJSONFromFile(path)
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveConnectedDevice(id: Int, type: Int, ip: String) {
        when (type) {
            SensorType.Conditioner.type -> {
                sharedPreferencesRepository.saveString(
                    SharedPreferencesRepository.ipOfConditioener,
                    ip
                )
                sharedPreferencesRepository.saveInt(
                    SharedPreferencesRepository.idOfConditioener,
                    id
                )
            }
            SensorType.Humidifier.type -> {
                sharedPreferencesRepository.saveString(
                    SharedPreferencesRepository.ipOfHumidifier,
                    ip
                )
                sharedPreferencesRepository.saveInt(SharedPreferencesRepository.ipOfHumidifier, id)
            }
        }
    }

    fun getSystemIp() = sharedPreferencesRepository.getString(SharedPreferencesRepository.systemIp)

    suspend fun sendConfig(
        systemIp: String,
        data: List<Pair<String, Int>>,
    ) = networkRepository.sendConfig(
        fileRepository.findDeviceConfig(data.map { it.second }).mapNotNull { item ->
            val ip = data.find { it.second == item.id }?.first
            if (!ip.isNullOrEmpty()) {
                sendConfigRequestMapper(item, ip)
            } else {
                null
            }
        },
        systemIp
    )


}