package com.example.smarthome.fragments.connectDevice.chooseDevice

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.connectDevice.ConnectDeviceInteractor
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseDeviceViewModel(
    private val connectDeviceInteractor: ConnectDeviceInteractor,
    private val router: Router,
    private val byIp: Boolean
) :
    BaseViewModel<ChooseDeviceState, ChooseDeviceEvent>() {

    private fun getDevices(type: Int) = (Gson().fromJson(
        connectDeviceInteractor.getJSONfromFile(FileRepository.FileName),
        object : TypeToken<Map<String, WifiDevicesItem>>() {}.type
    ) as Map<String, WifiDevicesItem>).filter { it.value.deviceType == type }
        .map {
            it.value
        }

    override fun createInitialState(): ChooseDeviceState {
        return ChooseDeviceState.OnSuccess(devices = getDevices(SensorType.Conditioner.type))
    }

    fun changeList(type: Type) {
        val devices = when (type) {
            Type.TypeCond -> getDevices(SensorType.Conditioner.type)
            Type.TypeHum -> getDevices(SensorType.Humidifier.type)
        }
        updateState {
            ChooseDeviceState.OnSuccess(devices)
        }
    }

    fun onItemClicked(type: Int, id: Int) {
        if (byIp) {
            sendEvent(ChooseDeviceEvent.OpenDeviceMenuByIP(type, id))
        } else {
            sendEvent(
                ChooseDeviceEvent.OpenDeviceMenu(
                    type,
                    id,
                    connectDeviceInteractor.getWifiInfo()
                )
            )
        }
    }

    fun connectByIp(type: Int, id: Int, ip: String) {
        updateState {
            ChooseDeviceState.Loading(true)
        }
        finishConnection(type, id, ip)
    }

    fun connect(type: Int, id: Int, wifiInfo: WifiInfo) {
        if (!(wifiInfo.ssid.isEmpty() || wifiInfo.password.isEmpty())) {
            viewModelScope.launch(Dispatchers.IO) {
                updateState {
                    ChooseDeviceState.Loading(true)
                }
                connectDeviceInteractor.connect(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        finishConnection(type, id, ip)
                    } else {
                        sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_connection_error))
                        updateState {
                            ChooseDeviceState.Loading(false)
                        }
                    }
                }
            }
        } else {
            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_data))
        }
    }

    private fun finishConnection(type: Int, id: Int, ip: String) {
        viewModelScope.launch {
            connectDeviceInteractor.saveConnectedDevice(id, type, ip)
            val systemIp = connectDeviceInteractor.getSystemIp()
            if (!systemIp.isNullOrEmpty()) {
                    connectDeviceInteractor.sendConfig(systemIp, listOf(Pair(ip, id))) { result ->
                        if (result) {
                            sendEvent(ChooseDeviceEvent.OnSuccess)
                            updateState {
                                ChooseDeviceState.Loading(false)
                            }
                        } else {
                            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_send_config))
                            updateState {
                                ChooseDeviceState.Loading(false)
                            }
                        }
                    }
            } else {
                sendEvent(ChooseDeviceEvent.OnSuccess)
                updateState {
                    ChooseDeviceState.Loading(false)
                }
            }
        }
    }

    enum class Type {
        TypeCond, TypeHum
    }

    override fun onBackPressed(): Boolean {
        router.backTo(Screens.SettingsScreen())
        return !super.onBackPressed()
    }

}