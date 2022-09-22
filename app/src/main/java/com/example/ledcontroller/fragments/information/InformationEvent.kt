package com.example.ledcontroller.fragments.information

sealed class InformationEvent {
    data class openTemperatureMenuEvent(val temperature: String, val date: String) : InformationEvent()
    object openConditionerMenuEvent : InformationEvent()
}