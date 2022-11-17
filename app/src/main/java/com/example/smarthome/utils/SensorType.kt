package com.example.smarthome.utils

enum class SensorType(val type: Int) {
    TemperatureSensor(1),
    PressureSensor(2),
    HumidifierSensor(3),
    Conditioner(4),
    Humidifier(5),
    Unknown(0)
}