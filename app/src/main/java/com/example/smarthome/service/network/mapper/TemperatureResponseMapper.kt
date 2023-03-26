package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.core.utils.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.TemperatureResponse
import kotlin.math.roundToInt

fun temperatureResponseMapper(
    response: TemperatureResponse,
    time: Time
) = DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema(
    id = response.id,
    data = response.temperature.roundToInt(),
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    type = getSenorType(response.deviceType),
    notification = response.notification,
    max = response.maxTemp,
    min = response.minTemp)

