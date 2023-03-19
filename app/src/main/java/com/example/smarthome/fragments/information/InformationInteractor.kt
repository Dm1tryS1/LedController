package com.example.smarthome.fragments.information

import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.smarthome.service.storage.entity.DeviceInfo

class InformationInteractor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository,
    private val networkRepository: NetworkRepository,
) {
    suspend fun getInfo() = networkRepository.getInfo()

    suspend fun getTemperature(id: Int) = networkRepository.getTemperature(id)

    suspend fun getPressure(id: Int) = networkRepository.getPressure(id)

    suspend fun getHumidity(id: Int) = networkRepository.getHumidity(id)

    suspend fun condCommand(command: String) = networkRepository.condCommand(command)

    suspend fun humCommand(command: String) = networkRepository.humCommand(command)

    suspend fun setTimer(value: Int) = networkRepository.setTimer(value)

    fun saveInDataBase(deviceInfo: DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

    fun getUserSettings() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userTimer, value)
    }

}