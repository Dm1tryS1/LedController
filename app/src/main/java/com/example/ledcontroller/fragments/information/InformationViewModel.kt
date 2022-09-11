package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ledcontroller.fragments.information.data.Data
import com.example.ledcontroller.fragments.information.data.TempData
import java.text.SimpleDateFormat
import java.util.*

class InformationViewModel(private val getInfoUseCase: GetInfoUseCase) : ViewModel() {

    fun findSensor(callback: (list: List<Data>) -> Unit) {
        val data = mutableListOf<Data>()
        data.add(Data(1, null, null))
        data.add(Data(2, null, null))
        callback(data.reversed())
    }

    fun getInfo(command: Int) {
        getInfoUseCase.getInfo(command)
    }

    fun startObserve(): MutableLiveData<TempData> {
        return getInfoUseCase.startObserve()
    }
}