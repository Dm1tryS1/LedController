package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SystemTimerRequest(
    val timer: Int
)