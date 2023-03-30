package com.example.smarthome.fragments.connectDevice.chooseDevice

import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.data.wifi.WifiInfo
import com.example.network.request
import com.example.smarthome.repository.ConnectDeviceRepository
import com.example.smarthome.repository.network.mapper.sendConfigRequestMapper

class ChooseDeviceUseCase(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository,
    private val connectDeviceRepository: ConnectDeviceRepository,
) {
    fun getJSONfromFile(path: String) = fileRepository.getJSONFromFile(path)
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    suspend fun sendConfig(
        data: List<Pair<String, Int>>,
    ) = request {
        connectDeviceRepository.sendConfig(
            fileRepository.findDeviceConfig(data.map { it.second }).mapNotNull { item ->
                val ip = data.find { it.second == item.id }?.first
                if (!ip.isNullOrEmpty()) {
                    sendConfigRequestMapper(item, ip)
                } else {
                    null
                }
            }
        )
    }


}