package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
class GetAllResponse (
    val temperature: List<TemperatureResponse?>?,
    val pressure: List<PressureResponse?>?,
    val humidity: List<HumidityResponse?>?,
    val conditioner: ConditionerResponse?,
    val humidifier: HumidifierResponse?
)