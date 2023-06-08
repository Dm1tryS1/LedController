package com.example.connection_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IrReceiverRequest(
    val command: String,
    val deviceType: Int
)