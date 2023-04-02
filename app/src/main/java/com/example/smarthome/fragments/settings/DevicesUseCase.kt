package com.example.smarthome.fragments.settings

import com.example.connection_impl.data.WifiDeviceRepository
import com.example.smarthome.repository.*
import com.example.data.wifi.WifiInfo
import com.example.shared_preferences.SharedPreferencesService

class DevicesUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val wifiDeviceRepository: WifiDeviceRepository,
) {
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connectWifiModule(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveSystemIp(ip: String) =
        sharedPreferencesRepository.saveString(SharedPreferencesService.systemIp, ip)

}