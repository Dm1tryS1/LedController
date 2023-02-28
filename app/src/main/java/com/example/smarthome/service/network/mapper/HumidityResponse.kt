package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.HumidityResponse
import kotlin.math.roundToInt


fun humidityResponseMapper(
    response: HumidityResponse
) = DeviceInfoSchema.HumiditySensorSchema(
    id = response.id,
    data = response.humidity,
    hours = null,
    minutes = null,
    seconds = null,
    type = response.deviceType,
    notification = response.notification,
    more = response.more,
    comfortableValue = response.comfortableValue
)