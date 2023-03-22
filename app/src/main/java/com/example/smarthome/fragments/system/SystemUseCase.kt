package com.example.smarthome.fragments.system

import com.example.smarthome.core.utils.request
import com.example.smarthome.core.utils.setPickerNumber
import com.example.smarthome.repository.NetworkRepository
import com.example.smarthome.repository.SharedPreferencesRepository

class SystemUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val networkRepository: NetworkRepository
) {
    suspend fun setSystemSetting(
        maxTemp: Int,
        minTemp: Int,
        maxHum: Int,
        minHum: Int,
        displayedValue: Int
    ) = request {
        networkRepository.setSystemSettings(
            minTemp,
            maxTemp,
            minHum,
            maxHum,
            displayedValue
        )
    }

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
            .setPickerNumber(SystemFragment.MAX_TEMP_VALUE)

    fun getMinTemperature() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinTemperature)
            .setPickerNumber(SystemFragment.MIN_TEMP_VALUE)

    fun getMaxHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMaxHumidity)
            .setPickerNumber(SystemFragment.MAX_HUM_VALUE)

    fun getMinHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userMinHumidity)
            .setPickerNumber(SystemFragment.MIN_HUM_VALUE)

    fun getDisplayedValue() =
        sharedPreferencesRepository.getInt(SharedPreferencesRepository.userDisplayedValue)
            .setPickerNumber(SystemFragment.DISPLAYED_VALUE)

    fun clearSettings() {
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMaxTemperature)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMinTemperature)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMaxHumidity)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userMinHumidity)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesRepository.userDisplayedValue)
    }
}