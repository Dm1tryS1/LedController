package com.example.information_impl.domain

import com.example.information_impl.data.DeviceInfoDataBaseRepository
import com.example.information_impl.data.InformationRepository
import com.example.network.request
import com.example.shared_preferences.SharedPreferences

class InformationUseCase(
    private val sharedPreferences: SharedPreferences,
    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository,
    private val informationRepository: InformationRepository,
) {
    suspend fun getInfo() = request { informationRepository.getInfo() }

    suspend fun getTemperature(id: Int) =
        request { informationRepository.getTemperature(id) }

    suspend fun getPressure(id: Int) =
        request { informationRepository.getPressure(id) }

    suspend fun getHumidity(id: Int) =
        request { informationRepository.getHumidity(id) }

    suspend fun condCommand(command: String) =
        request { informationRepository.condCommand(command) }

    suspend fun humCommand(command: String) =
        request { informationRepository.humCommand(command) }

    suspend fun setTimer(value: Int) =
        request { informationRepository.setTimer(value) }

    fun saveInDataBase(deviceInfo: com.example.storage.entity.DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

    fun getUserSettings() =
        sharedPreferences.getInt(SharedPreferences.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferences.saveInt(SharedPreferences.userTimer, value)
    }

}