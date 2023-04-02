package com.example.connection_impl.presentation.choose_device

import com.example.connection_impl.presentation.choose_device.recyclerView.model.WifiDevicesItem
sealed class ChooseDeviceState {
    class OnSuccess(val devices: List<WifiDevicesItem>) : ChooseDeviceState()
    class Loading(val isLoading: Boolean) : ChooseDeviceState()
}