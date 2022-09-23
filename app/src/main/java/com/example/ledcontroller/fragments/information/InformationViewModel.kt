package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ledcontroller.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.ledcontroller.utils.Command
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationViewModel(private val getInfoUseCase: GetInfoUseCase) : ViewModel() {

    val state = MutableLiveData(InformationState(listOf()))
    val event = MutableLiveData<InformationEvent>()

    fun initializeState() {
        sendPackage(Command.BroadCast.command)
    }

    fun sendPackage(aPackage: Pair<Int, Int>){
        getInfoUseCase.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch {
            getInfoUseCase.getInfo().collectLatest { it ->
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
                            state.postValue(InformationState(newState))
                        }
                    else
                        state.postValue(InformationState(listOf(packageToInfoViewItem(it))))
                }

            }
        }
    }


    fun onMenuClicked(id: Int) {
        state.value?.let { state ->
            when (id) {
                1 -> state.data?.find { id == it.id }?.let {
                    event.postValue(InformationEvent.OpenTemperatureMenuEvent(it.info, it.date))
                }

                2 -> event.postValue(InformationEvent.OpenConditionerMenuEvent)
                3 -> state.data?.find { id == it.id }?.let {
                    event.postValue(InformationEvent.OpenHumidityMenuEvent(it.info, it.date))
                }
                4 -> event.postValue(InformationEvent.OpenHumidifierMenuEvent)
                else -> {}
            }
        }
    }
}