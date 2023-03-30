package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
class HumidifierResponse(
    val id: Int,
    val deviceType: Int,
    val status: Boolean,
    val water: Int
)