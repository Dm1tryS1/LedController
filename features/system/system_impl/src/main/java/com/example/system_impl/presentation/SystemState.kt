package com.example.system_impl.presentation

data class SystemState(
    val maxTemp: Int,
    val minTemp: Int,
    val maxHum: Int,
    val minHum: Int,
    val displayedValue: Int,
    val isLoading: Boolean
)


