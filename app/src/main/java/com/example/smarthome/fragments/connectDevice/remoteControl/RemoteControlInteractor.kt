package com.example.smarthome.fragments.connectDevice.remoteControl

import com.example.smarthome.core.utils.request
import com.example.smarthome.repository.NetworkRepository

class RemoteControlInteractor(
    private val networkRepository: NetworkRepository,
) {
    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) = request {
        networkRepository.writeCommandForRemoteControl(deviceType, command)
    }
}