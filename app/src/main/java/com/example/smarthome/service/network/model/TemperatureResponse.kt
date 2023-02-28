package com.example.smarthome.service.network.model

import kotlinx.serialization.Serializable

@Serializable
class TemperatureResponse(
    val id: Int,
    val deviceType: Int,
    val temperature: Float,
    val notification: Boolean,
    val more: Boolean?,
    val comfortableValue: Int?
)