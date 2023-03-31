package com.example.smarthome.repository.network.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.data.DeviceInfoSchema
import com.example.smarthome.repository.network.model.TemperatureResponse
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

