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
        return RemoteControlState(Type.TypeCond)
    }


    override fun onBackPressed(): Boolean {
        router.backTo(Screens.MainScreen())
        return !super.onBackPressed()
    }

    fun changeList(type: Type) {
        when (type) {
            Type.TypeCond -> updateState { RemoteControlState(Type.TypeCond) }
            Type.TypeHum -> updateState { RemoteControlState(Type.TypeHum) }
        }
    }

    fun writeCommandForRemoteControl(deviceType: Int, command: String) {
        viewModelScope.launch {
            updateState { state -> state.copy(loading = true) }
            val result = remoteControlInteractor.writeCommandForRemoteControl(deviceType, command).data
            if (result != null && result.result == "success") {
                updateState { state -> state.copy(loading = false) }
                sendEvent(RemoteControlEvent.OnSuccess)
            } else {
                updateState { state -> state.copy(loading = false) }
                sendEvent(RemoteControlEvent.OnError)
            }
        }
    }

    enum class Type {
        TypeCond, TypeHum
    }

}