package com.example.ledcontroller.fragments.information

import androidx.lifecycle.ViewModel
import com.example.ledcontroller.fragments.information.data.Data

class InformationViewModel: ViewModel() {

    fun findSensor(callback : (list: List<Data>) -> Unit) {
        val data = mutableListOf<Data>()
        data.add(Data(1,"-"))
        data.add(Data(2,"-"))
        data.add(Data(3, "-"))
        callback(data)
    }
}