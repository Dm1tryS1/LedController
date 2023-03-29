package com.example.smarthome.fragments.information

import com.example.data.device.SensorType
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem

sealed class InformationEvent {
    data class OpenConditionerMenuEvent(
        val id: Int,
        val on: Boolean,
        val command: (String) -> Unit
    ) : InformationEvent()

    data class OpenHumidifierMenuEvent(
        val id: Int,
        val on: Boolean,
        val command: (String) -> Unit
    ) : InformationEvent()

    data class OpenSettingsMenuEvent(val value: Int, val setTimer: (value: Int) -> Unit) :
        InformationEvent()

    data class OpenSensorMenuEvent(
        val deviceInfo: InfoViewItem.SensorsInfoViewItem,
        val command: (id: Int, sensorType: SensorType) -> Unit
    ) : InformationEvent()

    data class ShowNotification(
        val id: Int,
        val type: SensorType,
        val more: Boolean,
        val comfortableValue: Int
    ) : InformationEvent()
}