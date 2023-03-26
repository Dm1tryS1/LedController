package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.data.DeviceInfoSchema
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.main.ChartsParams
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

    private fun makeNotification(schema: DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema) {
        if (schema.notification)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.data < schema.min,
                    comfortableValue = if (schema.data < schema.min) schema.min else schema.max
                )
            )
    }

    private fun saveInDataBase(deviceInfoSchema: DeviceInfoSchema.Sensors) {
        informationUseCase.saveInDataBase(
            DeviceInfo(
                deviceId = deviceInfoSchema.id,
                time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                value = deviceInfoSchema.data,
                date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            )
        )
    }


    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val newState = mutableListOf<InfoViewItem.SensorsInfoViewItem>()
            informationUseCase.getInfo().data?.forEach { schema ->
                if (schema is DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema) {
                    makeNotification(schema)
                }

                if (schema is DeviceInfoSchema.Sensors) {
                    saveInDataBase(schema)
                }
                newState.add(packageToInfoViewItem(schema))
            }
            updateState { InformationState(newState, false) }
        }

    }

    private fun update(response: DeviceInfoSchema) {
        if (response is DeviceInfoSchema.Sensors) { saveInDataBase(response) }
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

    private fun getSenorInfo(id: Int, type: SensorType) {
        when (type) {
            SensorType.TemperatureSensor -> getTemperature(id)
            SensorType.HumiditySensor -> getHumidity(id)
            SensorType.PressureSensor -> getPressure(id)
            else -> { }
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

    fun onMenuClicked(deviceInfo: InfoViewItem.SensorsInfoViewItem) {
        when (deviceInfo.sensorType) {
            SensorType.TemperatureSensor, SensorType.PressureSensor, SensorType.HumiditySensor -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    deviceInfo,
                    this::getSenorInfo,
                )
            )
            SensorType.Conditioner -> sendEvent(
                InformationEvent.OpenConditionerMenuEvent(
                    deviceInfo.id,
                    deviceInfo.status,
                    this::sendCondCommand
                )
            )
            SensorType.Humidifier -> sendEvent(
                InformationEvent.OpenHumidifierMenuEvent(
                    deviceInfo.id,
                    deviceInfo.status,
                    this::sendHumCommand
                )
            )
            else -> { }
        }
    }

    private fun setTimer(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            informationUseCase.setTimer(value)
        }
    }

    fun onChartOpen(type: SensorType, id: Int) {
        if (type == SensorType.TemperatureSensor || type == SensorType.HumiditySensor || type == SensorType.PressureSensor)
            router.navigateTo(Screens.chartScreen(ChartsParams(type, id)))
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
        router.navigateTo(Screens.systemScreen())
    }
}