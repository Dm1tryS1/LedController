package com.example.smarthome.repository

import com.example.network.NetworkFactory
import com.example.smarthome.repository.network.model.IrReceiverRequest
import com.example.smarthome.repository.network.model.SendConfigRequest

class ConnectDeviceRepository(
    networkModule: NetworkFactory
) {
    private var connectDeviceService = networkModule.createService(com.example.smarthome.repository.network.ConnectDeviceService::class.java)

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>
    ) = connectDeviceService.sendConfig(wifiDevicesItem)

    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        connectDeviceService
            .irreceiver(IrReceiverRequest(command, deviceType))

}