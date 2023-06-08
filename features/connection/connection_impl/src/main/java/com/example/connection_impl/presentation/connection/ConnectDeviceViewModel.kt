package com.example.connection_impl.presentation.connection

import com.example.connection_impl.presentation.choose_device.ChooseDeviceFragment
import com.example.connection_impl.presentation.data.ChooseDeviceParams
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.data.device.ControlType
import com.example.core.presentation.BaseViewModel
import com.example.connection_impl.presentation.remote_control.RemoteControlFragment
import com.github.terrakok.cicerone.Router

class ConnectDeviceViewModel(
    router: Router
) :
   BaseViewModel<Unit, Unit>(router = router) {

    fun onNextClicked(controlType: ControlType) {
        router.navigateTo(
            ChooseDeviceFragment::class.java.createScreen(
            ChooseDeviceParams(
                controlType
            )
        ))
    }

    fun onRemoteControlClicked() {
        router.navigateTo(RemoteControlFragment::class.java.createScreen(NoParams))
    }

    override fun createInitialState() = Unit
}