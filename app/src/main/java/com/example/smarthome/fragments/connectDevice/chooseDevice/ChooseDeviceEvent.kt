package com.example.smarthome.fragments.connectDevice.chooseDevice

import com.example.data.wifi.WifiInfo

sealed class ChooseDeviceEvent {
    class OpenDeviceMenu(val type: Int, val id: Int, val wifiInfo: WifiInfo) : ChooseDeviceEvent()

    class OpenDeviceMenuByIP(val type: Int, val id: Int) : ChooseDeviceEvent()
    class OnError(val message: Int) : ChooseDeviceEvent()
    object OnSuccess : ChooseDeviceEvent()
}