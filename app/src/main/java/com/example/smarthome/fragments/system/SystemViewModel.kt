package com.example.smarthome.fragments.system

import androidx.lifecycle.viewModelScope
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.CommandsForMaster
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SystemViewModel(private val router: Router, private val systemInteractor: SystemInteractor) :
    BaseViewModel<SystemState, Unit>() {

    init {
        viewModelScope.launch {
            var maxTemp = systemInteractor.getMaxTemperature()
            var minTemp = systemInteractor.getMinTemperature()
            var maxHum = systemInteractor.getMaxHumidity()
            var minHum = systemInteractor.getMinHumidity()
            var displayedValue = systemInteractor.getDisplayedValue()

            if (maxTemp == -1)
                maxTemp = System.MIN_TEMP_VALUE

            if (minTemp == -1)
                minTemp = System.MIN_TEMP_VALUE

            if (minHum == -1)
                minHum = System.MIN_HUM_VALUE

            if (maxHum == -1)
                maxHum = System.MIN_HUM_VALUE

            if (displayedValue == -1)
                displayedValue = System.DISPLAYED_VALUE

            updateState {
                SystemState.Settings(
                    maxTemp = maxTemp,
                    minTemp = minTemp,
                    maxHum = maxHum,
                    minHum = minHum,
                    displayedValue = displayedValue
                )
            }
        }
    }

    override fun createInitialState(): SystemState {
        return SystemState.Settings(
            System.MIN_TEMP_VALUE,
            System.MIN_TEMP_VALUE,
            System.MIN_HUM_VALUE,
            System.MIN_HUM_VALUE,
            System.DISPLAYED_VALUE
        )
    }

    fun save(maxTemp: Int, minTemp: Int, maxHum: Int, minHum: Int, displayedValue: Int) {
        updateState {
            SystemState.Loading
        }
        viewModelScope.launch {
            systemInteractor.saveMaxTemperature(maxTemp)
            systemInteractor.saveMinTemperature(minTemp)
            systemInteractor.saveMaxHumidity(maxHum)
            systemInteractor.saveMinHumidity(minHum)
            systemInteractor.saveDisplayedValue(displayedValue)
            sendPackages(maxTemp, minTemp, maxHum, minHum, displayedValue)
            router.backTo(Screens.InformationScreen())
        }
    }

    fun clearSettings() {
        viewModelScope.launch {
            systemInteractor.clearMaxTemperature()
            systemInteractor.clearMinTemperature()
            systemInteractor.clearMaxHumidity()
            systemInteractor.clearMinHumidity()
            systemInteractor.clearDisplayedValue()
            systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.ClearSettings, 0))
            router.backTo(Screens.InformationScreen())
        }
    }

    private suspend fun sendPackages(maxTemp: Int, minTemp: Int, maxHum: Int, minHum: Int, displayedValue: Int) {
        systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.SetMaxTemperature, maxTemp))
        delay(200)
        systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.SetMinTemperature, minTemp))
        delay(200)
        systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.SetMaxHumidity, maxHum))
        delay(200)
        systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.SetMinHumidity, minHum))
        delay(200)
        systemInteractor.sendPackage(Command.MasterCommand(CommandsForMaster.SetDisplayedValue, displayedValue))
        delay(200)
    }

}