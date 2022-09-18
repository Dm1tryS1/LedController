package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ledcontroller.fragments.information.data.Package

class InformationViewModel(private val getInfoUseCase: GetInfoUseCase) : ViewModel() {

    fun findSensor(callback: (list: List<Package>) -> Unit) {
        val data = mutableListOf<Package>()
        data.add(Package(1, null, null))
        data.add(Package(2, null, null))
        callback(data.reversed())
    }

    fun getInfo(command: Int) {
        getInfoUseCase.getInfo(command)
    }

    fun startObserve(): MutableLiveData<Package> {
        return getInfoUseCase.startObserve()
    }
}