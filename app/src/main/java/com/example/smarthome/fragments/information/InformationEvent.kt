package com.example.smarthome.fragments.information

sealed class InformationEvent {
    data class OpenTemperatureMenuEvent(val temperature: String, val date: String) :
        InformationEvent()

    data class OpenHumidityMenuEvent(val humidity: String, val date: String) : InformationEvent()
    object OpenConditionerMenuEvent : InformationEvent()
    object OpenHumidifierMenuEvent : InformationEvent()
    data class OpenPressureMenuEvent(val pressure: String, val date: String) : InformationEvent()
    data class OpenSettingsMenuEvent(val value: Int) : InformationEvent()
}