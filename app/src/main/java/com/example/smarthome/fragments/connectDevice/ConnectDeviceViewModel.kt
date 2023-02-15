package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.utils.Screens
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