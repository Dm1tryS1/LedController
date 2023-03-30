package com.example.smarthome.fragments.system

import com.example.core.setPickerNumber
import com.example.network.request
import com.example.shared_preferences.SharedPreferencesService
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.smarthome.repository.SystemRepository

class SystemUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val systemRepository: SystemRepository
) {
    suspend fun setSystemSetting(
        maxTemp: Int,
        minTemp: Int,
        maxHum: Int,
        minHum: Int,
        displayedValue: Int
    ) = request {
        systemRepository.setSystemSettings(
            minTemp,
            maxTemp,
            minHum,
            maxHum,
            displayedValue
        )
    }

    fun saveMaxTemperature(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userMaxTemperature, value)
    }

    fun saveMinTemperature(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userMinTemperature, value)
    }

    fun saveMaxHumidity(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userMaxHumidity, value)
    }

    fun saveMinHumidity(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userMinHumidity, value)
    }

    fun saveDisplayedValue(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userDisplayedValue, value)
    }

    fun getMaxTemperature() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userMaxTemperature)
            .setPickerNumber(SystemFragment.MAX_TEMP_VALUE)

    fun getMinTemperature() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userMinTemperature)
            .setPickerNumber(SystemFragment.MIN_TEMP_VALUE)

    fun getMaxHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userMaxHumidity)
            .setPickerNumber(SystemFragment.MAX_HUM_VALUE)

    fun getMinHumidity() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userMinHumidity)
            .setPickerNumber(SystemFragment.MIN_HUM_VALUE)

    fun getDisplayedValue() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userDisplayedValue)
            .setPickerNumber(SystemFragment.DISPLAYED_VALUE)

    fun clearSettings() {
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesService.userMaxTemperature)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesService.userMinTemperature)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesService.userMaxHumidity)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesService.userMinHumidity)
        sharedPreferencesRepository.deleteUserSettings(SharedPreferencesService.userDisplayedValue)
    }
}