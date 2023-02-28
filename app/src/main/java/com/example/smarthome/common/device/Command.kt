package com.example.smarthome.common.device

sealed class Command(val msgBuffer: MutableList<Int>) {

    class MasterSendDate : Command(mutableListOf(0x00, 0x00))

    class MasterCommand(command: CommandsForMaster, value: Int) : Command(mutableListOf(0x00)) {
        init {
            msgBuffer.add(command.command)
            msgBuffer.add(value)
        }
    }
}

enum class CommandsForMaster(val command: Int) {
    SetTimer(0x01),
    SetMinTemperature(0x02),
    SetMaxTemperature(0x03),
    SetMinHumidity(0x04),
    SetMaxHumidity(0x05),
    SetDisplayedValue(0x06),
}

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