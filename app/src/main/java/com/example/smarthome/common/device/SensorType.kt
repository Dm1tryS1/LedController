package com.example.smarthome.common.device

enum class SensorType(val type: Int, val text: String) {
    TemperatureSensor(1, "Датчики температуры"),
    PressureSensor(2, "Датчики давления"),
    HumiditySensor(3, "Датчики влажности"),
    Conditioner(4, "Кондиционеры"),
    Humidifier(5, "Увлажнители воздуха"),
    Unknown(0, "Неизвестное устройство"),
}