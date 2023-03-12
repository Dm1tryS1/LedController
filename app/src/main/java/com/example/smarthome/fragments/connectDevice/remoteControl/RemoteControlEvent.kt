package com.example.smarthome.fragments.connectDevice.remoteControl

sealed class RemoteControlEvent {
    object OnError : RemoteControlEvent()
    object OnSuccess: RemoteControlEvent()
    object OpenDialog: RemoteControlEvent()
}