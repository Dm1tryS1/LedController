package com.example.smarthome.fragments.system

import com.example.smarthome.common.device.Command
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.Storage

class SystemInteractor(private val storage: Storage, private val deviceRepository: DeviceRepository) {

    fun sendPackage(aPackage: Command) {
        deviceRepository.sendPackage(aPackage)
    }

    fun saveMaxTemperature(value: Int) {
        storage.saveInt(Storage.userMaxTemperature, value)
    }

    fun saveMinTemperature(value: Int) {
        storage.saveInt(Storage.userMinTemperature, value)
    }

    fun saveMaxHumidity(value: Int) {
        storage.saveInt(Storage.userMaxHumidity, value)
    }

    fun saveMinHumidity(value: Int) {
        storage.saveInt(Storage.userMinHumidity, value)
    }

    fun saveDisplayedValue(value: Int) {
        storage.saveInt(Storage.userDisplayedValue, value)
    }

    fun getMaxTemperature() = storage.getInt(Storage.userMaxTemperature)

    fun getMinTemperature() = storage.getInt(Storage.userMinTemperature)

    fun getMaxHumidity() = storage.getInt(Storage.userMaxHumidity)

    fun getMinHumidity() = storage.getInt(Storage.userMinHumidity)

    fun getDisplayedValue() = storage.getInt(Storage.userDisplayedValue)

    fun clearMaxTemperature() = storage.deleteUserSettings(Storage.userMaxTemperature)

    fun clearMinTemperature() = storage.deleteUserSettings(Storage.userMinTemperature)

    fun clearMaxHumidity() = storage.deleteUserSettings(Storage.userMaxHumidity)

    fun clearMinHumidity() = storage.deleteUserSettings(Storage.userMinHumidity)

    fun clearDisplayedValue() = storage.deleteUserSettings(Storage.userDisplayedValue)
}