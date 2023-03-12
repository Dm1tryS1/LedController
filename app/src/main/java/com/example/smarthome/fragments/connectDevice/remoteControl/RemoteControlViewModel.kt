package com.example.smarthome.fragments.connectDevice.remoteControl

import androidx.lifecycle.viewModelScope
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class RemoteControlViewModel(
    private val router: Router,
    private val remoteControlInteractor: RemoteControlInteractor
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

    fun writeCommandForRemoteControl(deviceType: Int, command: String) {
        viewModelScope.launch {
            val result = remoteControlInteractor.writeCommandForRemoteControl(deviceType, command)
            if (result != null && result.result == "success") {
                sendEvent(RemoteControlEvent.OnSuccess)
            } else {
                sendEvent(RemoteControlEvent.OnError)
            }
        }
    }

    enum class Type {
        TypeCond, TypeHum
    }

}