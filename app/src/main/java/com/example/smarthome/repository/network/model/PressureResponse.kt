package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PressureResponse(
    val id: Int,
    val deviceType: Int,
    val pressure: Float,
)