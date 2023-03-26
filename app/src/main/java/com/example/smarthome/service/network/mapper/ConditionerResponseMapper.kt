package com.example.smarthome.service.network.mapper

import com.example.smarthome.common.Time
import com.example.smarthome.core.utils.getSenorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.ConditionerResponse

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