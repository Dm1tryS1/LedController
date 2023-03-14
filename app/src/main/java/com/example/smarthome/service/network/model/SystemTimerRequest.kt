package com.example.smarthome.service.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SystemTimerRequest(
    val timer: Int
)