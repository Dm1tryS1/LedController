package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.TemperatureResponse
import kotlin.math.roundToInt

fun temperatureResponseMapper(
    response: TemperatureResponse
) = DeviceInfoSchema.TemperatureSensorSchema(
    id = response.id,
    data = response.temperature.roundToInt(),
    hours = null,
    minutes = null,
    seconds = null,
    type = response.deviceType,
    notification = response.notification,
    more = response.more,
    comfortableValue = response.comfortableValue
)