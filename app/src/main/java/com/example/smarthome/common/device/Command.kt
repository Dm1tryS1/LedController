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

    class SensorCommand(id: Int) : Command(mutableListOf()) {
        init {
            msgBuffer.add(id)
            msgBuffer.add(0x00)
        }
    }

    class ConditionerCommand(id: Int,command: CommandForConditioner) : Command(mutableListOf()) {
        init {
            msgBuffer.add(id)
            msgBuffer.add(command.command)
        }
    }

    class Humidifier(id: Int, command: CommandForHumidifier) : Command(mutableListOf()) {
        init {
            msgBuffer.add(id)
            msgBuffer.add(command.command)
        }
    }
}

enum class CommandsForMaster(val command: Int) {
    SetTimer(0x01),
    SetMinTemperature(0x02),
    SetMaxTemperature(0x03),
    SetMinHumidity(0x04),
    SetMaxHumidity(0x05)
}

enum class CommandForConditioner(val command: Int) {
    Conditioner(0),
    OnOff(1),
    AddTemperature(2),
    ReduceTemperature(3)
}

enum class CommandForHumidifier(val command: Int) {
    Humidifier(0),
    OnOff(1)
}