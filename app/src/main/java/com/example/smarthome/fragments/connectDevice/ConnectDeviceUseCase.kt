package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.WifiDeviceRepository
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.core.utils.request
import com.example.smarthome.service.network.mapper.sendConfigRequestMapper

class ConnectDeviceUseCase(
    private val wifiDeviceRepository: WifiDeviceRepository,
    private val fileRepository: FileRepository,
    private val networkRepository: NetworkRepository,
) {
    fun getJSONfromFile(path: String) = fileRepository.getJSONFromFile(path)
    fun getWifiInfo() = wifiDeviceRepository.getWifiInfo()

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        wifiDeviceRepository.connect(wifiInfo) { callback(it) }
    }

    suspend fun sendConfig(
        data: List<Pair<String, Int>>,
    ) = request {
        networkRepository.sendConfig(
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