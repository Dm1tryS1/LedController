package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InformationViewModel(
    private val informationInteractor: InformationInteractor,
    private val router: Router
) :
    BaseViewModel<InformationState, InformationEvent>() {

    override fun createInitialState(): InformationState {
        getInfo()
        return InformationState(listOf(), true)
    }

    private fun makeNotification(schema: DeviceInfoSchema.TemperatureSensorSchema) {
        if (schema.id != null && schema.type != null && schema.comfortableValue != null && schema.more != null)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.more,
                    comfortableValue = schema.comfortableValue.toInt()
                )
            )
    }

    private fun makeNotification(schema: DeviceInfoSchema.HumiditySensorSchema) {
        if (schema.id != null && schema.type != null && schema.comfortableValue != null && schema.more != null)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.more,
                    comfortableValue = schema.comfortableValue.toInt()
                )
            )
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

                    newState.add(packageToInfoViewItem(schema))
                }
            }
            updateState { InformationState(newState, false) }
        }

    }

    private fun getTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getTemperature()
            if (response != null) {
                val newState = currentViewState.data?.map { item ->
                    if (item.id == response.id) {
                        packageToInfoViewItem(response)
                    } else {
                        item
                    }
                }
                updateState { InformationState(newState, false) }
            }
        }
    }

    private fun getPressure() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getPressure()
            if (response != null) {
                val newState = currentViewState.data?.map { item ->
                    if (item.id == response.id) {
                        packageToInfoViewItem(response)
                    } else {
                        item
                    }
                }
                updateState { InformationState(newState, false) }
            }
        }
    }

    private fun getHumidity() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.getHumidity()
            if (response != null) {
                val newState = currentViewState.data?.map { item ->
                    if (item.id == response.id) {
                        packageToInfoViewItem(response)
                    } else {
                        item
                    }
                }
                updateState { InformationState(newState, false) }
            }
        }
    }

    private fun sendCondCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.condCommand(command)
            if (response != null) {
                val newState = currentViewState.data?.map { item ->
                    if (item.id == response.id) {
                        packageToInfoViewItem(response)
                    } else {
                        item
                    }
                }
                updateState { InformationState(newState, false) }
            }
        }
    }

    private fun sendHumCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationInteractor.humCommand(command)
            if (response != null) {
                val newState = currentViewState.data?.map { item ->
                    if (item.id == response.id) {
                        packageToInfoViewItem(response)
                    } else {
                        item
                    }
                }
                updateState { InformationState(newState, false) }
            }
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
                    currentViewState.data?.find { it.id == id }?.info?.contains("Выключено") == true
                )
            )
            else -> {}
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
                sendEvent(InformationEvent.OpenSettingsMenuEvent(informationInteractor.getUserSettings()))
            } else {
                sendEvent(InformationEvent.OpenSettingsMenuEvent(0))
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