package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConditionerRequest(
    val command: String
)