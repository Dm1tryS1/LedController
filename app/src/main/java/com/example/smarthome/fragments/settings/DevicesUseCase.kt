package com.example.smarthome.fragments.settings

import com.example.smarthome.repository.*
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.service.network.mapper.sendConfigRequestMapper

class DevicesUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val networkRepository: NetworkRepository,
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository
) {
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connectWifiModule(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveSystemIp(ip: String) = sharedPreferencesRepository.saveString(SharedPreferencesRepository.systemIp, ip)

    fun getCondInfo() =
        Pair(sharedPreferencesRepository.getString(SharedPreferencesRepository.ipOfConditioener), sharedPreferencesRepository.getInt(SharedPreferencesRepository.idOfConditioener))

    fun getHumIpInfo() =
        Pair(sharedPreferencesRepository.getString(SharedPreferencesRepository.ipOfHumidifier), sharedPreferencesRepository.getInt(SharedPreferencesRepository.idOfHumidifier))

    suspend fun sendConfig(
        systemIp: String,
        data: List<Pair<String, Int>>,
        callback: (result: Boolean) -> Unit
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