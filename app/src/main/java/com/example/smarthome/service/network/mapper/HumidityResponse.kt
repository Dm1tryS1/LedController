package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.HumidityResponse
import kotlin.math.roundToInt


fun humidityResponseMapper(
    response: HumidityResponse,
    time: Time
) = DeviceInfoSchema.HumiditySensorSchema(
    id = response.id,
    data = response.humidity,
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    type = response.deviceType,
    notification = response.notification,
    more = response.more,
    comfortableValue = response.comfortableValue
)