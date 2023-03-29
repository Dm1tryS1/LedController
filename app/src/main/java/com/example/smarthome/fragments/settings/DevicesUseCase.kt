package com.example.smarthome.fragments.settings

import com.example.smarthome.repository.*
import com.example.data.wifi.WifiInfo

class DevicesUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val wifiDeviceRepository: WifiDeviceRepository,
) {
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connectWifiModule(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveSystemIp(ip: String) =
        sharedPreferencesRepository.saveString(SharedPreferencesRepository.systemIp, ip)

}