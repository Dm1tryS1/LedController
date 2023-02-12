package com.example.smarthome.fragments.system

data class SystemState(
    val maxTemp: Int,
    val minTemp: Int,
    val maxHum: Int,
    val minHum: Int,
    val displayedValue: Int
)
