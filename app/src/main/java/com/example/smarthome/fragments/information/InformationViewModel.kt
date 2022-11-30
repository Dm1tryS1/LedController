package com.example.smarthome.fragments.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.utils.Command
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationViewModel(private val informationInteractor: InformationInteractor) : BaseViewModel() {

    val state = MutableLiveData(InformationState(listOf()))
    val event = MutableLiveData<InformationEvent>()

    var deviceCount = 0

    fun initializeState() {
        deviceCount = 8
        state.postValue(InformationState(state.value?.data, true))
        sendPackage(Command.BroadCast.command)
    }

    fun sendPackage(aPackage: Pair<Int, Int>) {
        deviceCount = if (aPackage.first == Command.BroadCast.command.first) {
            state.postValue(InformationState(state.value?.data, true))
            8
        }
        else if (aPackage.first != Command.MasterSendDate.command.first) {
            state.postValue(InformationState(state.value?.data, true))
            1
        } else 0
        informationInteractor.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch {
            informationInteractor.getInfo().collectLatest {
                deviceCount = deviceCount.dec()

                state.value?.let { informationState ->
                    if (informationState.data != null)
                        informationState.data.let { currentState ->
                            val sensor = currentState.find { item ->
                                item.id == it.id
                            }

                            val newState = if (sensor == null) {
                                currentState + packageToInfoViewItem(it)
                            } else
                                currentState.map { item ->
                                    if (item.id == it.id)
                                        packageToInfoViewItem(it)
                                    else
                                        item
                                }

                            state.postValue(InformationState(newState.sortedBy {
                                it.sensorType.type
                            }, deviceCount != 0))
                        }
                    else
                        state.postValue(
                            InformationState(
                                listOf(packageToInfoViewItem(it)),
                                deviceCount != 0
                            )
                        )
                }

            }
        }
    }

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            1 -> event.postValue(
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
            2 -> event.postValue(
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
            3 -> event.postValue(
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
            4 -> event.postValue(InformationEvent.OpenConditionerMenuEvent)
            5 -> event.postValue(InformationEvent.OpenHumidifierMenuEvent)
            else -> {}
        }
    }

    fun onSettingsClicked() {
        informationInteractor.getUserSettings {
            event.postValue(InformationEvent.OpenSettingsMenuEvent(it))
        }
    }

    fun saveUserSettings(value: Int) {
        informationInteractor.saveUserSettings(value)
    }

}