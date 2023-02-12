package com.example.smarthome.fragments.information

import com.example.smarthome.common.device.Command
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.Storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class InformationInteractor(private val deviceRepository: DeviceRepository, private val storage: Storage) {
    fun sendPackage(aPackage: Command) {
        deviceRepository.sendPackage(aPackage)
    }

    fun getInfo(): Flow<Package> = callbackFlow {
        deviceRepository.getInfo {
            trySend(it)
        }
        awaitClose()
    }

    fun getUserSettings() = storage.getUserSettings(Storage.userTimer)

    fun saveUserSettings(value: Int){
        storage.saveUserSettings(Storage.userTimer, value)
    }

}