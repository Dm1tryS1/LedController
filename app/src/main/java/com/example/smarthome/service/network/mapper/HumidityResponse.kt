package com.example.smarthome.service.network.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.HumidityResponse


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