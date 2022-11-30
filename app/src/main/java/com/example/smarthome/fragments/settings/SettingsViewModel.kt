package com.example.smarthome.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.base.presentation.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase,
    private val router: Router
) : BaseViewModel<SettingsState, SettingsEvent>() {

    fun findDevices() {
        viewModelScope.launch {
            val devices = devicesUseCase.findDevices()
            updateState { state ->
                state.copy(devices = devices)
            }
        }
    }

    fun connect(address: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                devicesUseCase.connect(address) {
                    if (it)
                        sendEvent(SettingsEvent.ConnectionSuccessEvent)
                    else
                        sendEvent(SettingsEvent.ConnectionFailureEvent)
                }
            }
        }
    }

    fun onItemClicked(address: String) {
        sendEvent(SettingsEvent.OnItemClickedEvent(address))
    }

    fun disconnect() {
        viewModelScope.launch {
            if (devicesUseCase.disconnect())
                sendEvent(SettingsEvent.DisconnectSuccessEvent)
            else
                sendEvent(SettingsEvent.DisconnectFailureEvent)
        }
    }

    override fun createInitialState(): SettingsState {
        return SettingsState(listOf())
    }
}