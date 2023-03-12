package com.example.smarthome.fragments.connectDevice.remoteControl

import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository

class RemoteControlInteractor(
    val networkRepository: NetworkRepository,
    val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        networkRepository.writeCommandForRemoteControl(
            sharedPreferencesRepository.getString(
                SharedPreferencesRepository.systemIp
            ) ?: "",
            deviceType, command
        )
}