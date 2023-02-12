package com.example.smarthome.fragments.system

import com.example.smarthome.common.device.Command
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.repository.Storage

class SystemInteractor(private val storage: Storage, private val deviceRepository: DeviceRepository) {

    fun sendPackage(aPackage: Command) {
        deviceRepository.sendPackage(aPackage)
    }

    fun saveMaxTemperature(value: Int) {
        storage.saveUserSettings(Storage.userMaxTemperature, value)
    }

    fun saveMinTemperature(value: Int) {
        storage.saveUserSettings(Storage.userMinTemperature, value)
    }

    fun saveMaxHumidity(value: Int) {
        storage.saveUserSettings(Storage.userMaxHumidity, value)
    }

    fun saveMinHumidity(value: Int) {
        storage.saveUserSettings(Storage.userMinHumidity, value)
    }

    fun saveDisplayedValue(value: Int) {
        storage.saveUserSettings(Storage.userDisplayedValue, value)
    }

    fun getMaxTemperature() = storage.getUserSettings(Storage.userMaxTemperature)

    fun getMinTemperature() = storage.getUserSettings(Storage.userMinTemperature)

    fun getMaxHumidity() = storage.getUserSettings(Storage.userMaxHumidity)

    fun getMinHumidity() = storage.getUserSettings(Storage.userMinHumidity)

    fun getDisplayedValue() = storage.getUserSettings(Storage.userDisplayedValue)
}