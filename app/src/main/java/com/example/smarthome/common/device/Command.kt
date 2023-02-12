package com.example.smarthome.common.device

sealed class Command(val msgBuffer: MutableList<Int>) {

    object BroadCast : Command(mutableListOf(0xFF, 0x00))

    class MasterSendDate : Command(mutableListOf(0x00, 0x00))

    class MasterCommand(command: CommandsForMaster, value: Int) : Command(mutableListOf(0x00)) {
        init {
            msgBuffer.add(command.command)
            msgBuffer.add(value)
        }
    }

    object GetTemperature1 : Command(mutableListOf(0x01, 0))
    object GetTemperature2 : Command(mutableListOf(0x03, 0))

    object GetPressure1 : Command(mutableListOf(0x07, 0))
    object GetPressure2 : Command(mutableListOf(0x08, 0))

    object GetHumidity1 : Command(mutableListOf(0x05, 0))
    object GetHumidity2 : Command(mutableListOf(0x06, 0))

    object Conditioner : Command(mutableListOf(0x02, 0))
    object ConditionerOnOff : Command(mutableListOf(0x02, 1))
    object ConditionerAddTemperature : Command(mutableListOf(0x02, 2))
    object ConditionerReduceTemperature : Command(mutableListOf(0x02, 3))

    object Humidifier : Command(mutableListOf(0x04, 0))
    object HumidifierOnOff : Command(mutableListOf(0x04, 1))
}

enum class CommandsForMaster(val command: Int) {
    SetTimer(0x01),
    SetMinTemperature(0x02),
    SetMaxTemperature(0x03),
    SetMinHumidity(0x04),
    SetMaxHumidity(0x05)
}