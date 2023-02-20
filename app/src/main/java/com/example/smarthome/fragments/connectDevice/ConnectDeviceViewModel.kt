package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(
    private val router: Router
) :
    BaseViewModel<Unit, ConnectDeviceEvent>() {

    fun onNextClicked(byIp: Boolean) {
        router.navigateTo(Screens.ChooseDeviceScreen(byIp))
    }

    override fun createInitialState() = Unit
}