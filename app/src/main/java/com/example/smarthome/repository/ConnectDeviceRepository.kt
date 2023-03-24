package com.example.smarthome.repository

import com.example.smarthome.service.network.ConnectDeviceService
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.model.IrReceiverRequest
import com.example.smarthome.service.network.model.SendConfigRequest

class ConnectDeviceRepository(
    networkModule: NetworkModule
) {
    private var connectDeviceService = networkModule.createService(ConnectDeviceService::class.java)

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>
    ) = connectDeviceService.sendConfig(wifiDevicesItem)

    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        connectDeviceService
            .irreceiver(IrReceiverRequest(command, deviceType))

}