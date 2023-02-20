package com.example.smarthome.fragments.information

import com.example.smarthome.common.device.Command
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.smarthome.service.storage.entity.DeviceInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class InformationInteractor(
    private val deviceRepository: DeviceRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository
) {
    fun sendPackage(aPackage: Command) {
        deviceRepository.sendPackage(aPackage)
    }

    fun getInfo(): Flow<Package> = callbackFlow {
        deviceRepository.getInfo {
            trySend(it)
        }
        awaitClose()
    }

    fun getUserSettings() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userTimer, value)
    }

    fun saveInDataBase(deviceInfo: DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

}