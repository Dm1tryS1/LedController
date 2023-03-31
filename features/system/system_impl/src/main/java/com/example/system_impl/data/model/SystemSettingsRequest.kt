package com.example.system_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
class SystemSettingsRequest(
    val minTemp: Int,
    val maxTemp: Int,
    val minHum: Int,
    val maxHum: Int,
    val displayedValue: Int
)