package com.example.smarthome.service

import com.example.storage.DeviceInfoDataBaseProvider
import com.example.storage.entity.DeviceInfo



class DeviceInfoDataBaseRepository(
    deviceInfoDataBaseProvider: DeviceInfoDataBaseProvider,
) {

    private val dbDao = deviceInfoDataBaseProvider.createDataBase().deviceInfoDao()

    fun saveDeviceInfo(deviceInfo: DeviceInfo) {
        dbDao.insert(deviceInfo)
    }

}