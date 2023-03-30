package com.example.smarthome.repository

import com.example.data.getTime
import com.example.network.NetworkFactory
import com.example.smarthome.repository.network.InformationService
import com.example.smarthome.repository.network.mapper.humidifierResponseMapper
import com.example.smarthome.repository.network.mapper.humidityResponseMapper
import com.example.smarthome.repository.network.mapper.pressureResponseMapper
import com.example.smarthome.repository.network.mapper.temperatureResponseMapper
import com.example.smarthome.repository.network.model.ConditionerRequest
import com.example.smarthome.repository.network.model.HumidifierRequest
import com.example.smarthome.repository.network.model.SystemTimerRequest

class InformationRepository(
    networkModule: NetworkFactory
) {

    private var informationService = networkModule.createService(InformationService::class.java)

    suspend fun getInfo() =
        com.example.smarthome.repository.network.mapper.getAllResponseMapper(
            informationService.getAll(),
            getTime()
        )

    suspend fun getTemperature(
        id: Int
    ) = temperatureResponseMapper(informationService.temperature(id), getTime())


    suspend fun getPressure(
        id: Int
    ) = pressureResponseMapper(informationService.pressure(id), getTime())


    suspend fun getHumidity(
        id: Int
    ) = humidityResponseMapper(informationService.humidity(id), getTime())


    suspend fun condCommand(
        command: String
    ) = com.example.smarthome.repository.network.mapper.conditionerResponseMapper(
        informationService.condcommand(ConditionerRequest(command)), getTime()
    )

    suspend fun humCommand(
        command: String
    ) = humidifierResponseMapper(
        informationService.humcommand(HumidifierRequest(command)),
        getTime()
    )

    suspend fun setTimer(
        value: Int
    ) =
        informationService.systemtimer(SystemTimerRequest(value))
}