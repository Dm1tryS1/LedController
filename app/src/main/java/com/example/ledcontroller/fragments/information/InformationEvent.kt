package com.example.ledcontroller.fragments.information

sealed class InformationEvent {
    class openTemperatureMenuEvent(val temperature: String, val date: String) : InformationEvent()
}