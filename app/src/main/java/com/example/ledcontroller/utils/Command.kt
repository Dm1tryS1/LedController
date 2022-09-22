package com.example.ledcontroller.utils

enum class Command(val command: Int) {
    BroadCast(15),
    GetTemperature(1),
    OffConditioner(2),
    OnConditioner(10),
    AddConditionerTemperature(18),
    ReduceConditionerTemperature(26),
}