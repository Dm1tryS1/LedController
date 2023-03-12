package com.example.smarthome.fragments.information

sealed class InformationEvent {
    data class OpenConditionerMenuEvent(val id: Int, val on: Boolean, val command: (String) -> Unit) : InformationEvent()
    data class OpenHumidifierMenuEvent(val id: Int, val on: Boolean, val command: (String) -> Unit) : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int, val setTimer: (value: Int) -> Unit) : InformationEvent()
    data class OpenSensorMenuEvent(
        val resources: Int,
        val command: () -> Unit,
        val data: String,
        val date: String
    ) : InformationEvent()

    data class ShowNotification(
        val id: Int,
        val type: Int,
        val more: Boolean,
        val comfortableValue: Int
    ) : InformationEvent()
}