package com.example.smarthome.repository

import com.example.smarthome.service.storage.DeviceInfoDataBaseProvider
import com.example.smarthome.service.storage.entity.DeviceInfo

class DeviceInfoDataBaseRepository(
    deviceInfoDataBaseProvider: DeviceInfoDataBaseProvider,
) {

    private val dbDao = deviceInfoDataBaseProvider.createDataBase().deviceInfoDao()

    fun saveDeviceInfo(deviceInfo: DeviceInfo) {
        dbDao.insert(deviceInfo)
    }

    fun getDeviceInfoByDate(id: Int, date: String) = dbDao.getInfoForDeviceByDate(id, date)

}