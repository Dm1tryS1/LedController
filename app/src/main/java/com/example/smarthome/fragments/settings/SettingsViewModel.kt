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

    fun connect(wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                devicesUseCase.connectWifiModule(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        devicesUseCase.saveSystemIp(ip)
                        sendEvent(SettingsEvent.ConnectionSuccessEvent)
                        updateState { state ->
                            state.copy(isLoading = false)
                        }
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