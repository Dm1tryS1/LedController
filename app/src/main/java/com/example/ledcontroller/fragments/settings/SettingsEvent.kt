package com.example.ledcontroller.fragments.settings

sealed class SettingsEvent {
    object ConnectionSuccessEvent: SettingsEvent()
    object ConnectionFailureEvent: SettingsEvent()
}