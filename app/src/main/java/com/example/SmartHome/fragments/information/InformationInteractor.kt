package com.example.SmartHome.fragments.information

import com.example.SmartHome.fragments.information.data.Package
import com.example.SmartHome.repository.DeviceRepository
import com.example.SmartHome.repository.Storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class InformationInteractor(private val deviceRepository: DeviceRepository, private val storage: Storage) {
    fun sendPackage(aPackage: Pair<Int, Int>) {
        deviceRepository.sendPackage(aPackage)
    }

    fun getInfo(): Flow<Package> = callbackFlow {
        deviceRepository.getInfo {
            trySend(it)
        }
        awaitClose()
    }

    fun getUserSettings(callback: (value: Int)-> Unit){
        storage.getUserSettings{
            callback(it)
        }
    }

    fun saveUserSettings(value: Int){
        storage.saveUserSettings(value)
    }

}