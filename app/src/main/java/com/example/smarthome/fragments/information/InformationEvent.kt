package com.example.smarthome.fragments.information

import com.example.smarthome.common.device.Command

sealed class InformationEvent {
    data class OpenConditionerMenuEvent(val id: Int) : InformationEvent()
    data class OpenHumidifierMenuEvent(val id: Int) : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int) : InformationEvent()
    data class OpenSensorMenuEvent(
        val resources: Int,
        val command: Command,
        val data: String,
        val date: String
    ) :
        InformationEvent()
}