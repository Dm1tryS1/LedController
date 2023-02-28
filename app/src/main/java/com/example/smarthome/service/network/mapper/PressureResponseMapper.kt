package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.PressureResponse
import kotlin.math.roundToInt

fun pressureResponseMapper(
    response: PressureResponse
) = DeviceInfoSchema.PressureSensorSchema(
    id = response.id,
    data = response.pressure.roundToInt(),
    hours = null,
    minutes = null,
    seconds = null,
    type = response.deviceType,
)