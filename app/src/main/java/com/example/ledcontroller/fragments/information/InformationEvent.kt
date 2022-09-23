package com.example.ledcontroller.fragments.information

sealed class InformationEvent {
    data class OpenTemperatureMenuEvent(val temperature: String, val date: String) : InformationEvent()
    data class OpenHumidityMenuEvent(val humidity: String, val date: String) : InformationEvent()
    object OpenConditionerMenuEvent : InformationEvent()
    object OpenHumidifierMenuEvent: InformationEvent()
}