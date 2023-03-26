package com.example.smarthome.fragments.connectDevice.remoteControl

import com.example.smarthome.core.utils.request
import com.example.smarthome.repository.ConnectDeviceRepository

class RemoteControlUseCase(
    private val connectDeviceRepository: ConnectDeviceRepository
) {
    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) = request {
        connectDeviceRepository.writeCommandForRemoteControl(deviceType, command)
    }
}