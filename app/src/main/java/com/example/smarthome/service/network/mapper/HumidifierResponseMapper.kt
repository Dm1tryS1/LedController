package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.service.network.model.ConditionerResponse
import com.example.smarthome.service.network.model.HumidifierResponse

fun humidifierResponseMapper(
    humidifierResponse: HumidifierResponse
) = DeviceInfoSchema.HumidifierSchema(
    id = humidifierResponse.id,
    status = humidifierResponse.status,
    hours = null,
    minutes = null,
    seconds = null,
    water = humidifierResponse.water,
    type = humidifierResponse.deviceType
)