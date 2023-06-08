package com.example.connection_impl.domain

import com.example.connection_impl.data.ConnectDeviceRepository
import com.example.network.request

class RemoteControlUseCase(
    private val connectDeviceRepository: ConnectDeviceRepository
) {
    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        request {
            connectDeviceRepository.writeCommandForRemoteControl(deviceType, command)
        }
}