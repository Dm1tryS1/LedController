package com.example.smarthome.fragments.settings

sealed class SettingsEvent {
    object ConnectionSuccessEvent: SettingsEvent()
    object ConnectionFailureEvent: SettingsEvent()
    class OnItemClickedEvent(val address: String): SettingsEvent()
    object DisconnectSuccessEvent: SettingsEvent()
    object DisconnectFailureEvent: SettingsEvent()
}