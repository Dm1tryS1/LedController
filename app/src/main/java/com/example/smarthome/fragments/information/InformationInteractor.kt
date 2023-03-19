package com.example.smarthome.fragments.information

import com.example.smarthome.core.utils.request
import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.smarthome.service.storage.entity.DeviceInfo

class InformationInteractor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository,
    private val networkRepository: NetworkRepository,
) {
    suspend fun getInfo() = request { networkRepository.getInfo() }

    suspend fun getTemperature(id: Int) = request { networkRepository.getTemperature(id) }

    suspend fun getPressure(id: Int) = request { networkRepository.getPressure(id) }

    suspend fun getHumidity(id: Int) = request { networkRepository.getHumidity(id) }

    suspend fun condCommand(command: String) = request { networkRepository.condCommand(command) }

    suspend fun humCommand(command: String) = request { networkRepository.humCommand(command) }

    suspend fun setTimer(value: Int) = request { networkRepository.setTimer(value) }

    fun saveInDataBase(deviceInfo: DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

    fun getUserSettings() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userTimer, value)
    }

}