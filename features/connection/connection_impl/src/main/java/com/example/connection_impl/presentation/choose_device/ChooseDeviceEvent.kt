package com.example.connection_impl.presentation.choose_device

import com.example.data.wifi.WifiInfo

sealed class ChooseDeviceEvent {
    class OpenDeviceMenu(val id: Int, val wifiInfo: WifiInfo) : ChooseDeviceEvent()

    class OpenDeviceMenuByIP(val id: Int) : ChooseDeviceEvent()
    class OnError(val message: Int) : ChooseDeviceEvent()
    object OnSuccess : ChooseDeviceEvent()
}