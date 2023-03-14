package com.example.smarthome.fragments.settings

import com.example.smarthome.common.wifi.WifiInfo

sealed class SettingsEvent {
    object ConnectionSuccessEvent: SettingsEvent()
    object ConnectionFailureEvent: SettingsEvent()
    class OnItemClickedEvent(val wifiInfo: WifiInfo): SettingsEvent()
    object DisconnectSuccessEvent: SettingsEvent()
    object DisconnectFailureEvent: SettingsEvent()
    class Error(val message: Int): SettingsEvent()
}