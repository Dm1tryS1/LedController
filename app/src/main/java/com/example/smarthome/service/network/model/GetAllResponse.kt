package com.example.smarthome.service.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetAllResponse (
    val temperature: TemperatureResponse,
    val pressure: PressureResponse,
    val humidity: HumidityResponse
)