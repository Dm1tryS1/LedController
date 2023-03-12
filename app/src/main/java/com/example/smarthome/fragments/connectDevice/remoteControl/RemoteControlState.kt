package com.example.smarthome.fragments.connectDevice.remoteControl

sealed class RemoteControlState {
    class ShowCommands(val deviceType: RemoteControlViewModel.Type) : RemoteControlState()
}