package com.example.information_impl.data.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.data.DeviceInfoSchema
import com.example.information_impl.data.model.PressureResponse
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