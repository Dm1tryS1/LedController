package com.example.smarthome.repository.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ConditionerRequest(
    val command: String
)