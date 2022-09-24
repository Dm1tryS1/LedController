package com.example.ledcontroller.utils

enum class Command(val command: Pair<Int, Int>) {
    BroadCast(Pair(0xFF,0)),

    MasterSendDate(Pair(0x00,0)),

    GetTemperature(Pair(0x01,0)),

    Conditioner(Pair(0x02,0)),
    ConditionerOnOff(Pair(0x02,1)),
    ConditionerAddTemperature(Pair(0x02,2)),
    ConditionerReduceTemperature(Pair(0x02,3)),

    GetHumidity(Pair(0x03,0)),

    Humidifier(Pair(0x04,0)),
    HumidifierOnOff(Pair(0x04,1)),
}