package com.example.smarthome.fragments.settings

import androidx.lifecycle.viewModelScope
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.utils.Screens
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

    fun onWifiClicked() {
        router.navigateTo(Screens.ConnectDeviceScreen())
    }

    private fun finishConnection(ip: String, address: String) {
        viewModelScope.launch {
            devicesUseCase.saveSystemIp(ip)

            val cond = devicesUseCase.getCondInfo()
            val hum = devicesUseCase.getHumIpInfo()

            val data = mutableListOf<Pair<String, Int>>()

            if (!cond.first.isNullOrEmpty() && cond.second != -1) {
                data.add(Pair(cond.first!!, cond.second))
            }
            if (!hum.first.isNullOrEmpty() && hum.second != -1) {
                data.add(Pair(hum.first!!, hum.second))
            }

            if (data.isNotEmpty()) {
                val result = devicesUseCase.sendConfig(ip, data)
                if (result.data != null) {
                    bluetoothConnection(address)
                } else {
                    sendEvent(SettingsEvent.DisconnectFailureEvent)
                    updateState { state ->
                        state.copy(isLoading = false)
                    }
                }

            } else {
                bluetoothConnection(address)
            }
        }
    }

    private fun bluetoothConnection(address: String) {
        viewModelScope.launch {
            devicesUseCase.connect(address) {
                if (it) {
                    sendEvent(SettingsEvent.ConnectionSuccessEvent)
                } else {
                    sendEvent(SettingsEvent.ConnectionFailureEvent)
                }

                updateState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun connect(address: String, wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                devicesUseCase.connectWifiModule(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        finishConnection(ip, address)
                    } else {
                        sendEvent(SettingsEvent.DisconnectFailureEvent)
                        updateState { state ->
                            state.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun onItemClicked(address: String) {
        sendEvent(SettingsEvent.OnItemClickedEvent(address, devicesUseCase.getWifiInfo()))
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
        return SettingsState(listOf(), false)
    }
}