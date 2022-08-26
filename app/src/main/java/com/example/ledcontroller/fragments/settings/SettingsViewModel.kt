package com.example.ledcontroller.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ledcontroller.data.Device
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase
) : ViewModel() {

    fun findDevices(callback: (list: List<Device>) -> Unit) {
        viewModelScope.launch {
            callback(devicesUseCase.findDevices())
        }
    }

    fun connect(address: String,callback: (flag: Boolean) -> Unit) {
        viewModelScope.launch {
            callback(devicesUseCase.connect(address))
        }
    }
}