package com.example.smarthome.fragments.charts.formatter

import com.example.smarthome.repository.DeviceInfoDataBaseRepository

class ChartInteractor(private val dataBaseRepository: DeviceInfoDataBaseRepository) {
    fun getDeviceInfo(id: Int, date: String) = dataBaseRepository.getDeviceInfoByDate(id, date)
}