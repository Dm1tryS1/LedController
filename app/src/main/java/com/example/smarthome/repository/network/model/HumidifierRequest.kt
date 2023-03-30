package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class HumidifierRequest(
    val command: String
)