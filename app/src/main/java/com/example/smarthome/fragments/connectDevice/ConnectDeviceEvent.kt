package com.example.smarthome.fragments.connectDevice

sealed class ConnectDeviceEvent {
    data class OnError(val text: Int): ConnectDeviceEvent()
    object OpenDialog: ConnectDeviceEvent()
}