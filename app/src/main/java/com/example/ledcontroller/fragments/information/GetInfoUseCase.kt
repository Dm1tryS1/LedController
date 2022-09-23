package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.repository.DeviceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GetInfoUseCase(private val deviceRepository: DeviceRepository) {
    fun sendPackage(aPackage: Pair<Int, Int>) {
        deviceRepository.sendPackage(aPackage)
    }

    fun getInfo(): Flow<Package> = callbackFlow {
        deviceRepository.getInfo {
            trySend(it)
        }
        awaitClose()
    }
}