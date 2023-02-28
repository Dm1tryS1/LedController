package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.ConditionerResponse

fun conditionerResponseMapper(
    conditionerResponse: ConditionerResponse
) = DeviceInfoSchema.ConditionerSchema(
    id = conditionerResponse.id,
    status = conditionerResponse.status,
    hours = null,
    minutes = null,
    seconds = null,
    temperature = conditionerResponse.temperature,
    type = conditionerResponse.deviceType
)