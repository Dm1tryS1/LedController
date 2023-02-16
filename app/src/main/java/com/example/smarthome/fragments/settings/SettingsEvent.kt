package com.example.smarthome.fragments.settings

import com.example.smarthome.repository.model.WifiInfo

sealed class SettingsEvent {
    object ConnectionSuccessEvent: SettingsEvent()
    object ConnectionFailureEvent: SettingsEvent()
    class OnItemClickedEvent(val address: String, val wifiInfo: WifiInfo): SettingsEvent()
    object DisconnectSuccessEvent: SettingsEvent()
    object DisconnectFailureEvent: SettingsEvent()
}