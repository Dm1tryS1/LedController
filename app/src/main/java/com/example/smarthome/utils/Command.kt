package com.example.smarthome.utils

enum class Command(val command: Pair<Int, Int>) {
    BroadCast(Pair(0xFF,0)),

    MasterSendDate(Pair(0x00,0)),

    GetTemperature1(Pair(0x01,0)),
    GetTemperature2(Pair(0x03,0)),

    GetPressure1(Pair(0x07,0)),
    GetPressure2(Pair(0x08,0)),

    GetHumidity1(Pair(0x05,0)),
    GetHumidity2(Pair(0x06,0)),

    Conditioner(Pair(0x02,0)),
    ConditionerOnOff(Pair(0x02,1)),
    ConditionerAddTemperature(Pair(0x02,2)),
    ConditionerReduceTemperature(Pair(0x02,3)),

    Humidifier(Pair(0x04,0)),
    HumidifierOnOff(Pair(0x04,1)),
}