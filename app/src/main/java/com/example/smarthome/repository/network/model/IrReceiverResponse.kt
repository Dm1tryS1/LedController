package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class IrReceiverResponse(
    val result: String
)