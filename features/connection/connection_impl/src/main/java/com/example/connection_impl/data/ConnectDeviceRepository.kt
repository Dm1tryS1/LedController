package com.example.connection_impl.data

import com.example.connection_impl.data.api.ConnectDeviceService
import com.example.connection_impl.data.model.IrReceiverRequest
import com.example.connection_impl.data.model.SendConfigRequest
import com.example.network.NetworkFactory

class ConnectDeviceRepository(
    networkModule: NetworkFactory
) {
    private var connectDeviceService = networkModule.createService(ConnectDeviceService::class.java)

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>
    ) = connectDeviceService.sendConfig(wifiDevicesItem)

    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        connectDeviceService
            .irreceiver(IrReceiverRequest(command, deviceType))

}