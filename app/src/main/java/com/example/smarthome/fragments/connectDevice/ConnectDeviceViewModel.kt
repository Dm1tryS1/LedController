package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.common.device.ControlType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.ChooseDeviceParams
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(
    router: Router
) :
    BaseViewModel<Unit, Unit>(router = router) {

    fun onNextClicked(controlType: ControlType) {
        router.navigateTo(Screens.chooseDeviceScreen(ChooseDeviceParams(controlType)))
    }

    fun onRemoteControlClicked() {
        router.navigateTo(Screens.remoteControlScreen())
    }

    override fun createInitialState() = Unit
}