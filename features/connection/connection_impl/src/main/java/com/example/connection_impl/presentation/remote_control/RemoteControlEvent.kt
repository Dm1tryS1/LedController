package com.example.connection_impl.presentation.remote_control

sealed class RemoteControlEvent {
    class ShowToast(val message: Int) : RemoteControlEvent()
}