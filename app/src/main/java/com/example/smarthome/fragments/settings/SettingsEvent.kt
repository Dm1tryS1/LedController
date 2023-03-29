package com.example.smarthome.fragments.settings

import com.example.data.wifi.WifiInfo

sealed class SettingsEvent {
    class OpenDialog(val wifiInfo: WifiInfo): SettingsEvent()
    class ShowSnack(val message: Int): SettingsEvent()
}