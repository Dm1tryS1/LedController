package com.example.smarthome.fragments.settings

import com.example.smarthome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.smarthome.repository.*
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.service.network.mapper.sendConfigRequestMapper

class DevicesUseCase(
    private val deviceRepository: DeviceRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val networkRepository: NetworkRepository,
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository
) {
    fun findDevices(): List<DeviceViewItem>? {
        return deviceRepository.findDevices()
    }

    suspend fun connect(address: String, callback: (result: Boolean) -> Unit) {
        var timer: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userTimer)
        var maxHum: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMaxHumidity)
        var minHum: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinHumidity)
        var maxTemp: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMaxTemperature)
        var minTemp: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinTemperature)
        var displayedValue: Int? = sharedPreferencesRepository.getInt(SharedPreferencesRepository.userDisplayedValue)

        if (timer == -1) timer = null
        if (maxHum == -1) maxHum = null
        if (minHum == -1) minHum = null
        if (maxTemp == -1) maxTemp = null
        if (minTemp == -1) minTemp = null
        if (displayedValue == -1) displayedValue = null

        callback(
            deviceRepository.connect(
                address,
                timer = timer,
                maxTemp = maxTemp,
                minTemp = minTemp,
                maxHum = maxHum,
                minHum = minHum,
                displayedValue = displayedValue
            )
        )
    }

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


    fun disconnect(): Boolean {
        return deviceRepository.disconnect()
    }
}