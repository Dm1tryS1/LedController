package com.example.smarthome.fragments.information

sealed class InformationEvent {
    object OpenConditionerMenuEvent : InformationEvent()
    object OpenHumidifierMenuEvent : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int) : InformationEvent()
    data class OpenSensorMenuEvent(val resources: Int ,val command: Pair<Int,Int>, val data: String, val date: String) :
        InformationEvent()
}