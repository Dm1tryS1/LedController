package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class IrReceiverRequest(
    val command: String,
    val deviceType: Int
)