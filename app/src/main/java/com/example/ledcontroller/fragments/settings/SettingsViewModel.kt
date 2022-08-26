package com.example.ledcontroller.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase
) : ViewModel() {

    fun findDevices(callback: (map: MutableMap<String, String>) -> Unit) {
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