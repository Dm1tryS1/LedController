package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.GetAllResponse


fun getAllResponseMapper(
    response: GetAllResponse
): List<DeviceInfoSchema> {
    val list = mutableListOf<DeviceInfoSchema>()
    response.temperature.let { list.add(temperatureResponseMapper(it)) }
    response.humidity.let { list.add(humidityResponseMapper(it)) }
    response.pressure.let { list.add(pressureResponseMapper(it)) }
    return list.toList()
}