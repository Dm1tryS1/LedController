package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PressureResponse(
    val id: Int,
    val deviceType: Int,
    val pressure: Float,
)