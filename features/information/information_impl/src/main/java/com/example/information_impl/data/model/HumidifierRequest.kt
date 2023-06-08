package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HumidifierRequest(
    val command: String
)