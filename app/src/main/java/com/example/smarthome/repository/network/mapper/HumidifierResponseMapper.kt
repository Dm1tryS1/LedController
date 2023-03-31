package com.example.smarthome.repository.network.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.data.DeviceInfoSchema
import com.example.smarthome.repository.network.model.HumidifierResponse

fun humidifierResponseMapper(
    humidifierResponse: HumidifierResponse,
    time: Time
) = DeviceInfoSchema.HumidifierSchema(
    id = humidifierResponse.id,
    status = humidifierResponse.status,
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    water = humidifierResponse.water,
    type = getSenorType(humidifierResponse.deviceType)
)