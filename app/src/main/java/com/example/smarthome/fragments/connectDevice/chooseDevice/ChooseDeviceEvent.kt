package com.example.smarthome.fragments.connectDevice.chooseDevice

sealed class ChooseDeviceEvent {
    class OpenDeviceMenu(val id: Int) : ChooseDeviceEvent()
    class OnError(val message: Int) : ChooseDeviceEvent()
    object OnSuccess : ChooseDeviceEvent()
}