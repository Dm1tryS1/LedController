package com.example.smarthome.fragments.connectDevice.chooseDevice

import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
//TODO вынести в один класс
sealed class ChooseDeviceState {
    class OnSuccess(val devices: List<WifiDevicesItem>) : ChooseDeviceState()
    class Loading(val isLoading: Boolean) : ChooseDeviceState()
}