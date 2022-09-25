package com.example.smarthome.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            withContext(Dispatchers.IO) {
                devicesUseCase.connect(address) {
                    if (it)
                        event.postValue(SettingsEvent.ConnectionSuccessEvent)
                    else
                        event.postValue(SettingsEvent.ConnectionFailureEvent)
                }
            }
        }
    }

    fun onItemClicked(address: String) {
        event.postValue(SettingsEvent.OnItemClickedEvent(address))
    }

    fun disconnect() {
        viewModelScope.launch {
            if (devicesUseCase.disconnect())
                event.postValue(SettingsEvent.DisconnectSuccessEvent)
            else
                event.postValue(SettingsEvent.DisconnectFailureEvent)
        }
    }
}