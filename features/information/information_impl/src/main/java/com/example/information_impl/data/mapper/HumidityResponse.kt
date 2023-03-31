package com.example.information_impl.data.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.data.DeviceInfoSchema
import com.example.information_impl.data.model.HumidityResponse


fun humidityResponseMapper(
    response: HumidityResponse,
    time: Time
) = DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema(
    id = response.id,
    data = response.humidity,
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    type = getSenorType(response.deviceType),
    notification = response.notification,
    min = response.minHum,
    max = response.maxHum
)