package com.example.smarthome.fragments.connectDevice.chooseDevice

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.connectDevice.ConnectDeviceInteractor
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class ChooseDeviceViewModel(
    private val applicationContext: Context,
    private val connectDeviceInteractor: ConnectDeviceInteractor,
    private val router: Router,
    private val byIp: Boolean
) :
    BaseViewModel<ChooseDeviceState, ChooseDeviceEvent>() {

    private fun getJsonFromAssets(): String? {
        val jsonString: String = try {
            val `is`: InputStream = applicationContext.assets.open(FileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun getDevices(type: Int) = (Gson().fromJson(
        getJsonFromAssets(),
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

    fun onItemClicked(id: Int) {
        if (byIp) {
            sendEvent(ChooseDeviceEvent.OpenDeviceMenuByIP(id))
        } else {
            sendEvent(ChooseDeviceEvent.OpenDeviceMenu(id, connectDeviceInteractor.getWifiInfo()))
        }
    }

    fun connectByIp(id: Int, ip: String) {

    }

    fun connect(id: Int, wifiInfo: WifiInfo) {
        if (!(wifiInfo.ssid.isEmpty() || wifiInfo.password.isEmpty())) {
            viewModelScope.launch {
                connectDeviceInteractor.connect(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        connectDeviceInteractor.saveIdConnectedDevice(id)
                        connectDeviceInteractor.saveIpConnectedDevice(ip)
                        sendEvent(ChooseDeviceEvent.OnSuccess)
                    } else {
                        sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_connection_error))
                    }
                }
            }
        } else {
            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_data))
        }
    }


    companion object {
        private const val FileName = "wifi_devices.json"
    }

    enum class Type {
        TypeCond, TypeHum
    }

    override fun onBackPressed(): Boolean {
        router.backTo(Screens.SettingsScreen())
        return !super.onBackPressed()
    }

}