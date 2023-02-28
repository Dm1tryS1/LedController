package com.example.smarthome.fragments.system

import com.example.smarthome.common.device.Command
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository

class SystemInteractor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val networkRepository: NetworkRepository
) {

    private fun getSystemIp() = sharedPreferencesRepository.getString(SharedPreferencesRepository.systemIp) ?: ""

    suspend fun setSystemSetting(
        maxTemp: Int,
        minTemp: Int,
        maxHum: Int,
        minHum: Int,
        displayedValue: Int
    ) = networkRepository.setSystemSettings(getSystemIp(), minTemp, maxTemp, minHum, maxHum, displayedValue)

    fun saveMaxTemperature(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userMaxTemperature, value)
    }

    fun saveMinTemperature(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userMinTemperature, value)
    }

    fun saveMaxHumidity(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userMaxHumidity, value)
    }

    fun saveMinHumidity(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userMinHumidity, value)
    }

    fun saveDisplayedValue(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesRepository.userDisplayedValue, value)
    }

    fun getMaxTemperature() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMaxTemperature)

    fun getMinTemperature() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinTemperature)

    fun getMaxHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMaxHumidity)

    fun getMinHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinHumidity)

    fun getDisplayedValue() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userDisplayedValue)

    fun clearMaxTemperature() =
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMaxTemperature)

    fun clearMinTemperature() =
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMinTemperature)

    fun clearMaxHumidity() =
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMaxHumidity)

    fun clearMinHumidity() =
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMinHumidity)

    fun clearDisplayedValue() =
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userDisplayedValue)
}