package com.example.smarthome.fragments.system

sealed class SystemState {
    data class Settings(
        val maxTemp: Int,
        val minTemp: Int,
        val maxHum: Int,
        val minHum: Int,
        val displayedValue: Int
    ) : SystemState()

    object Loading : SystemState()

}
