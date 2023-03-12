package com.example.smarthome.fragments.connectDevice.remoteControl

import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class RemoteControlViewModel(
    private val router: Router,
) :
    BaseViewModel<RemoteControlState, RemoteControlEvent>() {


    override fun createInitialState(): RemoteControlState {
        return RemoteControlState.ShowCommands(Type.TypeCond)
    }



    override fun onBackPressed(): Boolean {
        router.backTo(Screens.SettingsScreen())
        return !super.onBackPressed()
    }

    fun changeList(type: Type) {
        when (type) {
            Type.TypeCond -> updateState { RemoteControlState.ShowCommands(Type.TypeCond) }
            Type.TypeHum -> updateState { RemoteControlState.ShowCommands(Type.TypeHum) }
        }
    }


    enum class Type {
        TypeCond, TypeHum
    }

}