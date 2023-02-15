package com.example.smarthome.fragments.connectDevice.chooseDevice

import com.example.smarthome.repository.model.WifiInfo

sealed class ChooseDeviceEvent {
    class OpenDeviceMenu(val id: Int, val wifiInfo: WifiInfo) : ChooseDeviceEvent()

    class OpenDeviceMenuByIP(val id: Int) : ChooseDeviceEvent()
    class OnError(val message: Int) : ChooseDeviceEvent()
    object OnSuccess : ChooseDeviceEvent()
}