package com.example.data.device

enum class ConditionerCommands(val command: String) {
    On("On"),
    Off("Off"),
    AddTemperature("AddTemperature"),
    ReduceTemperature("ReduceTemperature")
}

enum class HumidifierCommands(val command: String) {
    On("On"),
    Off("Off"),
}