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
    private val informationUseCase: InformationUseCase,
    router: Router
) :
    BaseViewModel<InformationState, InformationEvent>(router = router) {

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
                informationUseCase.saveInDataBase(
                    DeviceInfo(
                        deviceId = deviceInfoSchema.id!!,
                        time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                        value = deviceInfoSchema.data!!,
                        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                )
            }
            is DeviceInfoSchema.TemperatureSensorSchema -> {
                informationUseCase.saveInDataBase(
                    DeviceInfo(
                        deviceId = deviceInfoSchema.id!!,
                        time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                        value = deviceInfoSchema.data!!,
                        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                )
            }
            is DeviceInfoSchema.PressureSensorSchema -> {
                informationUseCase.saveInDataBase(
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
            val newState = mutableListOf<InfoViewItem.SensorsInfoViewItem>()
            informationUseCase.getInfo().data?.forEach { schema ->
                if (schema is DeviceInfoSchema.TemperatureSensorSchema) {
                    makeNotification(schema)
                }
                if (schema is DeviceInfoSchema.HumiditySensorSchema) {
                    makeNotification(schema)
                }
                saveInDataBase(schema)
                newState.add(packageToInfoViewItem(schema))
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

    private fun getTemperature(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getTemperature(id).data
            if (response != null) update(response)

        }
    }

    private fun getPressure(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getPressure(id).data
            if (response != null) update(response)
        }
    }

    private fun getHumidity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getHumidity(id).data
            if (response != null) update(response)
        }
    }

    private fun sendCondCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.condCommand(command).data
            if (response != null) update(response)
        }
    }

    private fun sendHumCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.humCommand(command).data
            if (response != null) update(response)
        }
    }

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            SensorType.TemperatureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    id,
                    R.drawable.ic_temperature,
                    this::getTemperature,
                    info,
                    date
                )
            )
            SensorType.PressureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    id,
                    R.drawable.ic_pressure,
                    this::getPressure,
                    info,
                    date
                )
            )
            SensorType.HumiditySensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    id,
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
            informationUseCase.setTimer(value)
        }
    }

    fun onChartOpen(type: Int, id: Int) {
        if (type == SensorType.TemperatureSensor.type || type == SensorType.HumiditySensor.type || type == SensorType.PressureSensor.type)
            router.navigateTo(Screens.ChartScreen(type, id))
    }

    fun onSettingsClicked() {
        viewModelScope.launch {
            val timer = informationUseCase.getUserSettings()
            if (timer >= 0) {
                sendEvent(InformationEvent.OpenSettingsMenuEvent(timer, this@InformationViewModel::setTimer))
            } else {
                sendEvent(InformationEvent.OpenSettingsMenuEvent(0, this@InformationViewModel::setTimer))
            }
        }
    }

    fun saveUserSettings(value: Int) {
        informationUseCase.saveUserSettings(value)
    }

    fun onMoreSettings() {
        router.navigateTo(Screens.SystemScreen())
    }
}