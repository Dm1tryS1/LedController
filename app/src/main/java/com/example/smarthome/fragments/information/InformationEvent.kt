package com.example.smarthome.fragments.information

import com.example.smarthome.common.device.Command

sealed class InformationEvent {
    data class OpenConditionerMenuEvent(val id: Int, val on: Boolean) : InformationEvent()
    data class OpenHumidifierMenuEvent(val id: Int, val on: Boolean) : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int) : InformationEvent()
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