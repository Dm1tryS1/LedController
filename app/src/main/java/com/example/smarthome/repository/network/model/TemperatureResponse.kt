package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
class TemperatureResponse(
    val id: Int,
    val deviceType: Int,
    val temperature: Float,
    val notification: Boolean,
    val minTemp: Int,
    val maxTemp: Int
)