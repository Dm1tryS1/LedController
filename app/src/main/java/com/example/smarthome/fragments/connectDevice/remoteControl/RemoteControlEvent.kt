package com.example.smarthome.fragments.connectDevice.remoteControl

sealed class RemoteControlEvent {
    class ShowToast(val message: Int) : RemoteControlEvent()
}