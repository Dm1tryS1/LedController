package com.example.smarthome.service.network.model

import kotlinx.serialization.Serializable

@Serializable
class GetAllResponse (
    val temperature: TemperatureResponse?,
    val pressure: PressureResponse?,
    val humidity: HumidityResponse?,
    val conditioner: ConditionerResponse?
)