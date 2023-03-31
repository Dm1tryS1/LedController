package com.example.information_impl.data.model

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