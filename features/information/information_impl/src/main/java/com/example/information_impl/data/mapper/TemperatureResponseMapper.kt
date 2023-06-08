package com.example.information_impl.data.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.data.DeviceInfoSchema
import com.example.information_impl.data.model.TemperatureResponse
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

