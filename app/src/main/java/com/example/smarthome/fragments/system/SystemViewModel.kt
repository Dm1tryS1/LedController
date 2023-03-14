package com.example.smarthome.fragments.system

import androidx.lifecycle.viewModelScope
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
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
                maxTemp = SystemFragment.MIN_TEMP_VALUE

            if (minTemp == -1)
                minTemp = SystemFragment.MIN_TEMP_VALUE

            if (minHum == -1)
                minHum = SystemFragment.MIN_HUM_VALUE

            if (maxHum == -1)
                maxHum = SystemFragment.MIN_HUM_VALUE

            if (displayedValue == -1)
                displayedValue = SystemFragment.DISPLAYED_VALUE

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
            SystemFragment.MIN_TEMP_VALUE,
            SystemFragment.MIN_TEMP_VALUE,
            SystemFragment.MIN_HUM_VALUE,
            SystemFragment.MIN_HUM_VALUE,
            SystemFragment.DISPLAYED_VALUE
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
            systemInteractor.setSystemSetting(maxTemp, minTemp, maxHum, minHum, displayedValue)
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
            systemInteractor.setSystemSetting(-1, -1, -1, -1, -1)
            router.backTo(Screens.InformationScreen())
        }
    }

}