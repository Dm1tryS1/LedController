package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.core.utils.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.HumidifierResponse

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