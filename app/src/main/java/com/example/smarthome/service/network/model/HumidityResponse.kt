package com.example.smarthome.service.network.model

import kotlinx.serialization.Serializable

@Serializable
data class HumidityResponse(
    val id: Int,
    val deviceType: Int,
    val humidity: Int,
    val notification: Boolean,
    val more: Boolean?,
    val comfortableValue: Int?
)