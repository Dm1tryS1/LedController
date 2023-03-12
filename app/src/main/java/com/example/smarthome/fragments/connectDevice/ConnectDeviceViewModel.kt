package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.common.device.ControlType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(
    private val router: Router
) :
    BaseViewModel<Unit, ConnectDeviceEvent>() {

    fun onNextClicked(controlType: ControlType) {
        router.navigateTo(Screens.ChooseDeviceScreen(controlType.type))
    }

    fun onRemoteControlClicked() {
        router.navigateTo(Screens.RemoteControlScreen())
    }

    override fun createInitialState() = Unit
}