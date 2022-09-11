package com.example.ledcontroller.fragments.table

import com.example.ledcontroller.fragments.table.data.Drawing
import com.example.ledcontroller.repository.DeviceRepository

class DataUseCase(private val deviceRepository: DeviceRepository) {
    fun testConnection(data: Int): Boolean {
        return deviceRepository.sendData(data)
    }

    fun sendDataForDrawing(data: Drawing): Boolean {
        return deviceRepository.sendDataForDrawing(data)
    }
}