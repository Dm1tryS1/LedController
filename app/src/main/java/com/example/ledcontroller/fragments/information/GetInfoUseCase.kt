package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.repository.DeviceRepository

class GetInfoUseCase(private val deviceRepository: DeviceRepository) {
    fun getInfo(command: Int) {
        deviceRepository.sendData(command)
    }

    fun startObserve(): MutableLiveData<Package> {
        return deviceRepository.startObserve()
    }
}