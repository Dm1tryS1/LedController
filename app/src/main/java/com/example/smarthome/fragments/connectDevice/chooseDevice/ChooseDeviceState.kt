package com.example.smarthome.fragments.connectDevice.chooseDevice

import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem

sealed class ChooseDeviceState {
    class OnSuccess(val devices: List<WifiDevicesItem>) : ChooseDeviceState()
}