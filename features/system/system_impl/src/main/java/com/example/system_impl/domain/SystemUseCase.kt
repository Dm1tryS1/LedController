package com.example.system_impl.domain

import com.example.core.setPickerNumber
import com.example.network.request
import com.example.shared_preferences.SharedPreferences
import com.example.system_impl.data.SystemRepository
import com.example.system_impl.presentation.SystemFragment

class SystemUseCase(
    private val sharedPreferences: SharedPreferences,
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
        sharedPreferences.saveInt(SharedPreferences.userMaxTemperature, value)
    }

    fun saveMinTemperature(value: Int) {
        sharedPreferences.saveInt(SharedPreferences.userMinTemperature, value)
    }

    fun saveMaxHumidity(value: Int) {
        sharedPreferences.saveInt(SharedPreferences.userMaxHumidity, value)
    }

    fun saveMinHumidity(value: Int) {
        sharedPreferences.saveInt(SharedPreferences.userMinHumidity, value)
    }

    fun saveDisplayedValue(value: Int) {
        sharedPreferences.saveInt(SharedPreferences.userDisplayedValue, value)
    }

    fun getMaxTemperature() =
        sharedPreferences.getInt(SharedPreferences.userMaxTemperature)
            .setPickerNumber(SystemFragment.MAX_TEMP_VALUE)

    fun getMinTemperature() =
        sharedPreferences.getInt(SharedPreferences.userMinTemperature)
            .setPickerNumber(SystemFragment.MIN_TEMP_VALUE)

    fun getMaxHumidity() =
        sharedPreferences.getInt(SharedPreferences.userMaxHumidity)
            .setPickerNumber(SystemFragment.MAX_HUM_VALUE)

    fun getMinHumidity() =
        sharedPreferences.getInt(SharedPreferences.userMinHumidity)
            .setPickerNumber(SystemFragment.MIN_HUM_VALUE)

    fun getDisplayedValue() =
        sharedPreferences.getInt(SharedPreferences.userDisplayedValue)
            .setPickerNumber(SystemFragment.DISPLAYED_VALUE)

    fun clearSettings() {
        sharedPreferences.deleteUserSettings(SharedPreferences.userMaxTemperature)
        sharedPreferences.deleteUserSettings(SharedPreferences.userMinTemperature)
        sharedPreferences.deleteUserSettings(SharedPreferences.userMaxHumidity)
        sharedPreferences.deleteUserSettings(SharedPreferences.userMinHumidity)
        sharedPreferences.deleteUserSettings(SharedPreferences.userDisplayedValue)
    }
}