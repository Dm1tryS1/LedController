package com.example.smarthome.fragments.information

import com.example.smarthome.common.device.Command

sealed class InformationEvent {
    object OpenConditionerMenuEvent : InformationEvent()
    object OpenHumidifierMenuEvent : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int) : InformationEvent()
    data class OpenSensorMenuEvent(val resources: Int, val command: Command, val data: String, val date: String) :
        InformationEvent()
}