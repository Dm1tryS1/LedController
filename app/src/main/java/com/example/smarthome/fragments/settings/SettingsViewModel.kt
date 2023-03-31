package com.example.smarthome.fragments.settings

import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.smarthome.R
import com.example.core.presentation.BaseViewModel
import com.example.data.wifi.WifiInfo
import com.example.smarthome.fragments.connectDevice.ConnectDeviceFragment
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase,
    router: Router
) : BaseViewModel<SettingsState, SettingsEvent>(router = router) {

    fun onWifiClicked() {
        router.navigateTo(ConnectDeviceFragment::class.java.createScreen(NoParams))
    }

    fun connect(wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            devicesUseCase.connectWifiModule(wifiInfo) { ip ->
                if (!ip.isNullOrEmpty()) {
                    devicesUseCase.saveSystemIp(ip)
                    sendEvent(SettingsEvent.ShowSnack(R.string.settings_connected))
                } else {
                    sendEvent(SettingsEvent.ShowSnack(R.string.settings_fail))
                }
                updateState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun onConnectClicked() {
        val wifiInfo = devicesUseCase.getWifiInfo()
        if (wifiInfo != null) {
            sendEvent(SettingsEvent.OpenDialog(wifiInfo))
        } else {
            sendEvent(SettingsEvent.ShowSnack(R.string.connect_device_error))
        }
    }

    override fun createInitialState(): SettingsState {
        return SettingsState(false)
    }
}