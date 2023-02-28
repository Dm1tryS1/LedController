package com.example.smarthome.fragments.settings

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase,
    private val router: Router
) : BaseViewModel<SettingsState, SettingsEvent>() {

    fun onWifiClicked() {
        router.navigateTo(Screens.ConnectDeviceScreen())
    }

    private fun finishConnection(ip: String) {
        viewModelScope.launch(Dispatchers.IO) {
            devicesUseCase.saveSystemIp(ip)

            val data = mutableListOf<Pair<String, Int>>()
           // data.add(Pair("192.168.1.1", 2))
           // data.add(Pair("192.168.1.2", 4))

            val cond = devicesUseCase.getCondInfo()
            val hum = devicesUseCase.getHumIpInfo()

            if (!cond.first.isNullOrEmpty() && cond.second != -1) {
                data.add(Pair(cond.first!!, cond.second))
            }
            if (!hum.first.isNullOrEmpty() && hum.second != -1) {
                data.add(Pair(hum.first!!, hum.second))
            }

            if (data.isNotEmpty()) {
                devicesUseCase.sendConfig(ip, data) { result ->
                    if (result) {
                        sendEvent(SettingsEvent.ConnectionSuccessEvent)
                    } else {
                        sendEvent(SettingsEvent.ConnectionFailureEvent)
                    }
                    updateState { state ->
                        state.copy(isLoading = false)
                    }
                }

            } else {
                sendEvent(SettingsEvent.ConnectionSuccessEvent)
                updateState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun connect(wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                devicesUseCase.connectWifiModule(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        finishConnection(ip)
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

    fun onConnectClicked() {
        val wifiInfo = devicesUseCase.getWifiInfo()
        if (wifiInfo != null) {
            sendEvent(SettingsEvent.OnItemClickedEvent(wifiInfo))
        } else {
            sendEvent(SettingsEvent.Error(R.string.connect_device_error))
        }
    }

    override fun createInitialState(): SettingsState {
        return SettingsState(false)
    }
}