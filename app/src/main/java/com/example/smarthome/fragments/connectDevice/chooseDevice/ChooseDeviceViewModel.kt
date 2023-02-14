package com.example.smarthome.fragments.connectDevice.chooseDevice

import android.content.Context
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.connectDevice.ConnectDeviceInteractor
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class ChooseDeviceViewModel(
    private val applicationContext: Context,
    private val connectDeviceInteractor: ConnectDeviceInteractor,
    private val router: Router
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
        sendEvent(ChooseDeviceEvent.OpenDeviceMenu(id))
    }

    fun connect(id: Int, ssid: String, password: String) {
        if (!(ssid.isEmpty() || password.isEmpty())) {
            val ip = connectDeviceInteractor.connect(ssid, password)
            if (!ip.isNullOrEmpty()) {
                connectDeviceInteractor.saveIdConnectedDevice(id)
                connectDeviceInteractor.saveIpConnectedDevice(ip)
                sendEvent(ChooseDeviceEvent.OnSuccess)
            } else {
                sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_connection_error))
            }
        } else {
            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_empty_text))
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