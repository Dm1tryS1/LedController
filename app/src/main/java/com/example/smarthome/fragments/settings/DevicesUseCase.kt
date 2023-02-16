package com.example.smarthome.fragments.settings

import com.example.smarthome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.Storage
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.smarthome.repository.model.BaseResponse
import com.example.smarthome.repository.model.SendConfig
import com.example.smarthome.repository.model.WifiInfo

class DevicesUseCase(
    private val deviceRepository: DeviceRepository,
    private val storage: Storage,
    private val networkRepository: NetworkRepository,
    private val wifiDeviceRepository: WifiDeviceRepository
) {
    fun findDevices(): List<DeviceViewItem>? {
        return deviceRepository.findDevices()
    }

    suspend fun connect(address: String, callback: (result: Boolean) -> Unit) {
        var timer: Int? = storage.getInt(Storage.userTimer)
        var maxHum: Int? = storage.getInt(Storage.userMaxHumidity)
        var minHum: Int? = storage.getInt(Storage.userMinHumidity)
        var maxTemp: Int? = storage.getInt(Storage.userMaxTemperature)
        var minTemp: Int? = storage.getInt(Storage.userMinTemperature)
        var displayedValue: Int? = storage.getInt(Storage.userDisplayedValue)

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

    suspend fun connectWifiModule(wifiInfo: WifiInfo,callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    fun saveSystemIp(ip: String) = storage.saveString(Storage.systemIp, ip)

    fun getCondInfo() = Pair(storage.getString(Storage.ipOfConditioener), storage.getInt(Storage.idOfConditioener))

    fun getHumIpInfo() = Pair(storage.getString(Storage.ipOfHumidifier), storage.getInt(Storage.idOfHumidifier))

    suspend fun sendConfig(
        systemIp: String,
        data: List<Pair<String, Int>>,
        callback: (BaseResponse<SendConfig>) -> Unit
    ) {
        networkRepository.sendConfig(systemIp, data) {
            callback(it)
        }
    }

    fun disconnect(): Boolean {
        return deviceRepository.disconnect()
    }
}