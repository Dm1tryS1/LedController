package com.example.smarthome.fragments.connectDevice

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.data.device.ControlType
import com.example.core.presentation.BaseViewModel
import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceFragment
import com.example.smarthome.fragments.connectDevice.remoteControl.RemoteControlFragment
import com.example.smarthome.main.ChooseDeviceParams
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(
    router: Router
) :
   BaseViewModel<Unit, Unit>(router = router) {

    fun onNextClicked(controlType: ControlType) {
        router.navigateTo(ChooseDeviceFragment::class.java.createScreen(ChooseDeviceParams(controlType)))
    }

    fun onRemoteControlClicked() {
        router.navigateTo(RemoteControlFragment::class.java.createScreen(NoParams))
    }

    override fun createInitialState() = Unit
}