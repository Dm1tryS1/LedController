package com.example.connection_impl.domain

import com.example.connection_impl.data.ConnectDeviceRepository
import com.example.connection_impl.data.FileRepository
import com.example.connection_impl.data.WifiDeviceRepository
import com.example.network.request
import com.example.connection_impl.data.mapper.sendConfigRequestMapper
import com.example.data.wifi.WifiInfo

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