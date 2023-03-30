package com.example.smarthome.repository.network.mapper

import com.example.data.Time
import com.example.core.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.repository.network.model.ConditionerResponse

fun conditionerResponseMapper(
    conditionerResponse: ConditionerResponse,
    time: Time
) = DeviceInfoSchema.ConditionerSchema(
    id = conditionerResponse.id,
    status = conditionerResponse.status,
    hours = time.hours,
    minutes = time.minutes,
    seconds = time.seconds,
    temperature = conditionerResponse.temperature,
    type = getSenorType(conditionerResponse.deviceType)
)