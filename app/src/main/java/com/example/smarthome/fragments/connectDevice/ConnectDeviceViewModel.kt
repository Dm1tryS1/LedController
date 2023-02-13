package com.example.smarthome.fragments.connectDevice

import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(private val connectDeviceInteractor: ConnectDeviceInteractor, private val router: Router) :
    BaseViewModel<Unit, ConnectDeviceEvent>() {

    fun onNextClicked() {
        if (connectDeviceInteractor.checkDeviceConnection()) {
            sendEvent(ConnectDeviceEvent.OpenDialog)
        } else {
            sendEvent(ConnectDeviceEvent.OnError(R.string.connect_device_error))
        }
    }

    fun connect(ssid: String, password: String) {
        if (!connectDeviceInteractor.connect(ssid, password).isNullOrEmpty()) {
            //TODO сохранение IP в бд
            router.backTo(Screens.SettingsScreen())
        } else {
            sendEvent(ConnectDeviceEvent.OnError(R.string.connect_device_connection_error))
        }
    }

    override fun createInitialState() = Unit
}