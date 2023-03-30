package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
class ConditionerResponse(
    val id: Int,
    val deviceType: Int,
    val status: Boolean? = false,
    val temperature: Int?
)