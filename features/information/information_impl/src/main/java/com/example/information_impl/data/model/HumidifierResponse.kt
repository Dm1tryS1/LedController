package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
class HumidifierResponse(
    val id: Int,
    val deviceType: Int,
    val status: Boolean,
    val water: Int
)