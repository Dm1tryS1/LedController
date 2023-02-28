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
    private fun getSystemIp() = sharedPreferencesRepository.getString(SharedPreferencesRepository.systemIp) ?: ""

    suspend fun getInfo() = networkRepository.getInfo(getSystemIp())

    suspend fun getTemperature() = networkRepository.getTemperature(getSystemIp())

    suspend fun getPressure() = networkRepository.getPressure(getSystemIp())

    suspend fun getHumidity() = networkRepository.getHumidity(getSystemIp())

    suspend fun condCommand(command: String) = networkRepository.condCommand(getSystemIp(),command)

    suspend fun humCommand(command: String) = networkRepository.humCommand(getSystemIp(),command)

    suspend fun setTimer(value: Int) = networkRepository.setTimer(getSystemIp(), value)

    fun saveInDataBase(deviceInfo: DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

    fun getUserSettings() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userTimer, value)
    }

}