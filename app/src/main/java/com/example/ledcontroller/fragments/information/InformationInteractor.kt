package com.example.ledcontroller.fragments.information

import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.repository.DeviceRepository
import com.example.ledcontroller.repository.Storage
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