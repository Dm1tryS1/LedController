package com.example.smarthome.fragments.system

import androidx.lifecycle.viewModelScope
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class SystemViewModel(private val router: Router, private val systemInteractor: SystemInteractor) :
    BaseViewModel<SystemState, Unit>() {

    init {
        updateState { state ->
            state.copy(loading = true)
        }

        viewModelScope.launch {
            val maxTemp = systemInteractor.getMaxTemperature()
            val minTemp = systemInteractor.getMinTemperature()
            val maxHum = systemInteractor.getMaxHumidity()
            val minHum = systemInteractor.getMinHumidity()
            val displayedValue = systemInteractor.getDisplayedValue()

            updateState {
                SystemState (
                    maxTemp = maxTemp,
                    minTemp = minTemp,
                    maxHum = maxHum,
                    minHum = minHum,
                    displayedValue = displayedValue,
                    loading = false
                )
            }
        }
    }

    override fun createInitialState(): SystemState {
        return SystemState(
            SystemFragment.MIN_TEMP_VALUE,
            SystemFragment.MIN_TEMP_VALUE,
            SystemFragment.MIN_HUM_VALUE,
            SystemFragment.MIN_HUM_VALUE,
            SystemFragment.DISPLAYED_VALUE,
            loading = false
        )
    }

    fun save(maxTemp: Int, minTemp: Int, maxHum: Int, minHum: Int, displayedValue: Int) {
        updateState {state ->
            state.copy(loading = true)
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
            systemInteractor.clearSettings()
            systemInteractor.setSystemSetting(-1, -1, -1, -1, -1)
            router.backTo(Screens.InformationScreen())
        }
    }

}