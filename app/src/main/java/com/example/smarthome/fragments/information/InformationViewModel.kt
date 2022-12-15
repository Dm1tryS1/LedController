package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationViewModel(
    private val informationInteractor: InformationInteractor,
    private val router: Router
) :
    BaseViewModel<InformationState, InformationEvent>() {

    override fun createInitialState(): InformationState {
        return InformationState(listOf(), true)
    }

    fun initializeState() {
        updateState { state ->
            state.copy(progressVisibility = true)
        }
        sendPackage(Command.BroadCast.command)
    }

    fun sendPackage(aPackage: Pair<Int, Int>) {
        if (aPackage.first == Command.BroadCast.command.first) {
            updateState { state ->
                state.copy(progressVisibility = true)
            }
        } else if (aPackage.first != Command.MasterSendDate.command.first) {
            updateState { state ->
                state.copy(progressVisibility = true)
            }
        }
        informationInteractor.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch {
            informationInteractor.getInfo().collectLatest { aPackage ->
                if (aPackage.id == 0 && aPackage.info0 == 255.toByte() && aPackage.info1 == 255.toByte() && aPackage.info2 == 255.toByte() && aPackage.info3 == 255.toByte())
                    updateState { state ->
                        state.copy(progressVisibility = false)
                    }
                else
                    currentViewState.let { informationState ->
                        if (informationState.data != null)
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
                        else
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

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            1 -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_temperature,
                    when (id) {
                        1 -> Command.GetTemperature1.command
                        3 -> Command.GetTemperature2.command
                        else -> Command.GetTemperature1.command
                    },
                    info,
                    date
                )
            )
            2 -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_pressure,
                    when (id) {
                        7 -> Command.GetPressure1.command
                        8 -> Command.GetPressure2.command
                        else -> Command.GetPressure1.command
                    },
                    info,
                    date
                )
            )
            3 -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_humidity,
                    when (id) {
                        5 -> Command.GetHumidity1.command
                        6 -> Command.GetHumidity2.command
                        else -> Command.GetHumidity1.command
                    },
                    info,
                    date
                )
            )
            4 -> sendEvent(InformationEvent.OpenConditionerMenuEvent)
            5 -> sendEvent(InformationEvent.OpenHumidifierMenuEvent)
            else -> {}
        }
    }

    fun onChartOpen(type: Int, id: Int) {
        if (type == SensorType.TemperatureSensor.type || type == SensorType.HumidifierSensor.type || type == SensorType.PressureSensor.type)
            router.navigateTo(Screens.ChartScreen())
    }

    fun onSettingsClicked() {
        informationInteractor.getUserSettings {
            sendEvent(InformationEvent.OpenSettingsMenuEvent(it))
        }
    }

    fun saveUserSettings(value: Int) {
        informationInteractor.saveUserSettings(value)
    }
}