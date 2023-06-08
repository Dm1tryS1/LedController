package com.example.chart_impl.data

import com.example.storage.DeviceInfoDataBaseProvider



class DeviceInfoDataBaseRepository(
    deviceInfoDataBaseProvider: DeviceInfoDataBaseProvider,
) {

    private val dbDao = deviceInfoDataBaseProvider.createDataBase().deviceInfoDao()

    fun getDeviceInfoByDate(id: Int, date: String) = dbDao.getInfoForDeviceByDate(id, date)

}