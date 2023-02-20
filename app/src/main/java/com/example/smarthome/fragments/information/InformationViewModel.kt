package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.main.Screens
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.service.storage.entity.DeviceInfo
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*

class InformationViewModel(
    private val informationInteractor: InformationInteractor,
    private val router: Router
) :
    BaseViewModel<InformationState, InformationEvent>() {

    init {
        updateState { state ->
            state.copy(progressVisibility = true)
        }
        sendPackage(Command.BroadCast)
    }

    override fun createInitialState(): InformationState {
        return InformationState(listOf(), true)
    }

    fun sendPackage(aPackage: Command) {
        if (aPackage is Command.BroadCast || !(aPackage is Command.MasterCommand || aPackage is Command.MasterSendDate)) {
            updateState { state ->
                state.copy(progressVisibility = true)
            }
        }
        if (aPackage is Command.BroadCast) {
            updateState { state ->
                state.copy(data = null)
            }
        }
        informationInteractor.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            informationInteractor.getInfo().collectLatest { aPackage ->
                if (aPackage.id == 0
                    && aPackage.type == DeviceRepository.EndOfTransmission.toInt()
                    && aPackage.info0 == DeviceRepository.EndOfTransmission
                    && aPackage.info1 == DeviceRepository.EndOfTransmission
                    && aPackage.info2 == DeviceRepository.EndOfTransmission
                    && aPackage.info3 == DeviceRepository.EndOfTransmission
                ) {
                    updateState { state ->
                        state.copy(progressVisibility = false)
                    }
                } else {
                    if (aPackage.type == SensorType.TemperatureSensor.type || aPackage.type == SensorType.HumidifierSensor.type) {
                        if (aPackage.info1 == DeviceRepository.Less || aPackage.info1 == DeviceRepository.More)
                            if (aPackage.id != null && aPackage.type != null && aPackage.info2 != null)
                                sendEvent(
                                    InformationEvent.ShowNotification(
                                        id = aPackage.id!!,
                                        type = aPackage.type!!,
                                        more = aPackage.info1 == DeviceRepository.More,
                                        comfortableValue = aPackage.info2!!.toInt()
                                    )
                                )
                    }
                    saveInDataBase(aPackage)
                    currentViewState.let { informationState ->
                        if (informationState.data != null) {
                            informationState.data.let { currentState ->
                                val sensor = currentState.find { item ->
                                    item.id == aPackage.id
                                }

                                val newState = if (sensor == null) {
                                    currentState + packageToInfoViewItem(aPackage)
                                } else
                                    currentState.map { item ->
                                        if (item.id == aPackage.id)
                                            packageToInfoViewItem(aPackage)
                                        else
                                            item
                                    }

                                updateState {
                                    InformationState(newState.sortedBy {
                                        it.sensorType.type
                                    }, true)
                                }
                            }
                        } else {
                            updateState {
                                InformationState(
                                    listOf(packageToInfoViewItem(aPackage)),
                                    true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveInDataBase(aPackage: Package) {
        if (aPackage.type == SensorType.TemperatureSensor.type || aPackage.type == SensorType.HumidifierSensor.type) {
            informationInteractor.saveInDataBase(
                DeviceInfo(
                    deviceId = aPackage.id!!,
                    time = "${aPackage.hours}:${aPackage.minutes}",
                    value = aPackage.info0!!.toInt(),
                    date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                )
            )
        } else if (aPackage.type == SensorType.PressureSensor.type) {
            informationInteractor.saveInDataBase(
                DeviceInfo(
                    deviceId = aPackage.id!!,
                    time = "${aPackage.hours}:${aPackage.minutes}",
                    value = ByteBuffer.wrap(
                        byteArrayOf(
                            aPackage.info3!!,
                            aPackage.info2!!,
                            aPackage.info1!!,
                            aPackage.info0!!
                        )
                    ).int,
                    date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                )
            )
        }
    }

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            SensorType.TemperatureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_temperature,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.PressureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_pressure,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.HumidifierSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_humidity,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.Conditioner.type -> sendEvent(InformationEvent.OpenConditionerMenuEvent(id))
            SensorType.Humidifier.type -> sendEvent(InformationEvent.OpenHumidifierMenuEvent(id))
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