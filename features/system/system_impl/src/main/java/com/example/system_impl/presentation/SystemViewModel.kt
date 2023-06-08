package com.example.system_impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.system_impl.domain.SystemUseCase
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class SystemViewModel(router: Router, private val systemUseCase: SystemUseCase) :
    BaseViewModel<SystemState, Unit>(router = router) {

    init {
        updateState { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            val maxTemp = systemUseCase.getMaxTemperature()
            val minTemp = systemUseCase.getMinTemperature()
            val maxHum = systemUseCase.getMaxHumidity()
            val minHum = systemUseCase.getMinHumidity()
            val displayedValue = systemUseCase.getDisplayedValue()

            updateState {
                SystemState(
                    maxTemp = maxTemp,
                    minTemp = minTemp,
                    maxHum = maxHum,
                    minHum = minHum,
                    displayedValue = displayedValue,
                    isLoading = false
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
            isLoading = false
        )
    }

    fun save(maxTemp: Int, minTemp: Int, maxHum: Int, minHum: Int, displayedValue: Int) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            systemUseCase.saveMaxTemperature(maxTemp)
            systemUseCase.saveMinTemperature(minTemp)
            systemUseCase.saveMaxHumidity(maxHum)
            systemUseCase.saveMinHumidity(minHum)
            systemUseCase.saveDisplayedValue(displayedValue)
            systemUseCase.setSystemSetting(maxTemp, minTemp, maxHum, minHum, displayedValue)
            router.exit()
        }
    }

    fun clear() {
        viewModelScope.launch {
            systemUseCase.clearSettings()
            systemUseCase.setSystemSetting(-1, -1, -1, -1, -1)
            router.exit()
        }
    }

}