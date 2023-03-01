package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.main.Screens
import com.example.smarthome.service.storage.entity.DeviceInfo
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InformationViewModel(
    private val informationInteractor: InformationInteractor,
    private val router: Router
) :
    BaseViewModel<InformationState, InformationEvent>() {

    override fun createInitialState(): InformationState {
        return InformationState(listOf(), true)
    }

    private fun makeNotification(schema: DeviceInfoSchema.TemperatureSensorSchema) {
        if (schema.id != null && schema.type != null && schema.notification && schema.data != null)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.data < schema.minTemp,
                    comfortableValue = if (schema.data < schema.minTemp) schema.minTemp else schema.maxTemp
                )
            )
    }

    private fun makeNotification(schema: DeviceInfoSchema.HumiditySensorSchema) {
        if (schema.id != null && schema.type != null && schema.notification && schema.data != null)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.data < schema.minHum,
                    comfortableValue = if (schema.data < schema.minHum) schema.minHum else schema.maxHum
                )
            )
    }

    private fun saveInDataBase(deviceInfoSchema: DeviceInfoSchema) {
        when (deviceInfoSchema) {
            is DeviceInfoSchema.HumiditySensorSchema -> {
                informationInteractor.saveInDataBase(
                    DeviceInfo(
                        deviceId = deviceInfoSchema.id!!,
                        time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                        value = deviceInfoSchema.data!!,
                        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                )
            }
            is DeviceInfoSchema.TemperatureSensorSchema -> {
                informationInteractor.saveInDataBase(
                    DeviceInfo(
                        deviceId = deviceInfoSchema.id!!,
                        time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                        value = deviceInfoSchema.data!!,
                        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                )
            }
            is DeviceInfoSchema.PressureSensorSchema -> {
                informationInteractor.saveInDataBase(
                    DeviceInfo(
                        deviceId = deviceInfoSchema.id!!,
                        time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                        value = deviceInfoSchema.data!!,
                        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                )
            }
            else -> {}
        }
    }

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getInfo()
            val newState = mutableListOf<InfoViewItem.SensorsInfoViewItem>()
            if (response.isNotEmpty()) {
                response.forEach { schema ->
                    if (schema is DeviceInfoSchema.TemperatureSensorSchema) {
                        makeNotification(schema)
                    }
                    if (schema is DeviceInfoSchema.HumiditySensorSchema) {
                        makeNotification(schema)
                    }
                    saveInDataBase(schema)
                    newState.add(packageToInfoViewItem(schema))
                }
            }
            updateState { InformationState(newState, false) }
        }

    }

    private fun update(response: DeviceInfoSchema) {
        saveInDataBase(response)
        val newState = currentViewState.data?.map { item ->
            if (item.id == response.id) {
                packageToInfoViewItem(response)
            } else {
                item
            }
        }
        updateState { InformationState(newState, false) }
    }

    private fun getTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getTemperature()
            if (response != null) update(response)

        }
    }

    private fun getPressure() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getPressure()
            if (response != null) update(response)
        }
    }

    private fun getHumidity() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getHumidity()
            if (response != null) update(response)
        }
    }

    private fun sendCondCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.condCommand(command)
            if (response != null) update(response)
        }
    }

    private fun sendHumCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.humCommand(command)
            if (response != null) update(response)
        }
    }

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            SensorType.TemperatureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_temperature,
                    this::getTemperature,
                    info,
                    date
                )
            )
            SensorType.PressureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_pressure,
                    this::getPressure,
                    info,
                    date
                )
            )
            SensorType.HumidifierSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_humidity,
                    this::getHumidity,
                    info,
                    date
                )
            )
            SensorType.Conditioner.type -> sendEvent(
                InformationEvent.OpenConditionerMenuEvent(
                    id,
                    currentViewState.data?.find { it.id == id }?.info?.contains("Выключено") == true,
                    this::sendCondCommand
                )
            )
            SensorType.Humidifier.type -> sendEvent(
                InformationEvent.OpenHumidifierMenuEvent(
                    id,
                    currentViewState.data?.find { it.id == id }?.info?.contains("Выключено") == true,
                    this::sendHumCommand
                )
            )
            else -> {}
        }
    }

    private fun setTimer(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            informationInteractor.setTimer(value)
        }
    }

    fun onChartOpen(type: Int, id: Int) {
        if (type == SensorType.TemperatureSensor.type || type == SensorType.HumidifierSensor.type || type == SensorType.PressureSensor.type)
            router.navigateTo(Screens.ChartScreen(type, id))
    }

    fun onSettingsClicked() {
        viewModelScope.launch {
            val timer = informationInteractor.getUserSettings()
            if (timer >= 0) {
                sendEvent(InformationEvent.OpenSettingsMenuEvent(timer, this@InformationViewModel::setTimer))
            } else {
                sendEvent(InformationEvent.OpenSettingsMenuEvent(0, this@InformationViewModel::setTimer))
            }
        }
    }

    fun saveUserSettings(value: Int) {
        informationInteractor.saveUserSettings(value)
    }

    fun onMoreSettings() {
        router.navigateTo(Screens.SystemScreen())
    }
}