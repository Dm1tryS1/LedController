package com.example.ledcontroller.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ledcontroller.fragments.settings.data.Device
import com.example.ledcontroller.fragments.settings.recyclerView.model.DeviceViewItem
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase
) : ViewModel() {

    val state = MutableLiveData<SettingsState>()
    val event = MutableLiveData<SettingsEvent>()

    fun findDevices() {
        viewModelScope.launch {
            state.postValue(SettingsState(devicesUseCase.findDevices()))
        }
    }

    fun connect(address: String) {
        viewModelScope.launch {
            if (devicesUseCase.connect(address))
                event.postValue(SettingsEvent.ConnectionSuccessEvent)
            else
                event.postValue(SettingsEvent.ConnectionFailureEvent)
        }
    }
}