package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class HumidityResponse(
    val id: Int,
    val deviceType: Int,
    val humidity: Int,
    val notification: Boolean,
    val minHum: Int,
    val maxHum: Int
)