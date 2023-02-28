package com.example.smarthome.service.network.model

import kotlinx.serialization.Serializable

@Serializable
class ConditionerResponse(
    val id: Int,
    val deviceType: Int,
    val status: Boolean,
    val temperature: Int
)