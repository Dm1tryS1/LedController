package com.example.chart_impl.domain

import com.example.chart_impl.data.DeviceInfoDataBaseRepository

class ChartUseCase(private val dataBaseRepository: DeviceInfoDataBaseRepository) {
    fun getDeviceInfo(id: Int, date: String) = dataBaseRepository.getDeviceInfoByDate(id, date)
}