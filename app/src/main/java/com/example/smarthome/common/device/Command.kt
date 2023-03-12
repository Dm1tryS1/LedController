package com.example.smarthome.common.device

enum class ConditionerCommands(val command: String) {
    Conditioner("Status"),
    On("On"),
    Off("Off"),
    AddTemperature("AddTemperature"),
    ReduceTemperature("ReduceTemperature")
}

enum class HumidifierCommands(val command: String) {
    Conditioner("Status"),
    On("On"),
    Off("Off"),
}