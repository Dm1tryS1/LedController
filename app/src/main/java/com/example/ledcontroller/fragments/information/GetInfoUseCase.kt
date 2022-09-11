package com.example.ledcontroller.fragments.information

import androidx.lifecycle.MutableLiveData
import com.example.ledcontroller.fragments.information.data.TempData
import com.example.ledcontroller.repository.DeviceRepository

class GetInfoUseCase(private val deviceRepository: DeviceRepository) {
    fun getInfo(command: Int) {
        deviceRepository.sendData(command)
    }

    fun startObserve(): MutableLiveData<TempData> {
        return deviceRepository.startObserve()
    }
}