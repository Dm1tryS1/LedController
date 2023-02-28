package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.GetAllResponse


fun getAllResponseMapper(
    response: GetAllResponse,
    time: Time
): List<DeviceInfoSchema> {
    val list = mutableListOf<DeviceInfoSchema>()
    response.temperature?.let { list.add(temperatureResponseMapper(it, time)) }
    response.humidity?.let { list.add(humidityResponseMapper(it, time)) }
    response.pressure?.let { list.add(pressureResponseMapper(it, time)) }
    response.conditioner?.let { list.add(conditionerResponseMapper(it, time)) }
    response.humidifier?.let { list.add(humidifierResponseMapper(it, time)) }
    return list.toList()
}