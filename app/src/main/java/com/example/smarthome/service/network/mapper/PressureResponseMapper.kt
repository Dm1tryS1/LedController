package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.core.utils.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.PressureResponse
import kotlin.math.roundToInt

fun pressureResponseMapper(
    response: PressureResponse,
    time: Time
) = DeviceInfoSchema.Sensors.PressureSensorSchema(
    id = response.id,
    data = response.pressure.roundToInt(),
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    type = getSenorType(response.deviceType),
)