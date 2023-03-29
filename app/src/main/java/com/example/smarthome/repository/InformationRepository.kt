package com.example.smarthome.repository

import com.example.data.getTime
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.InformationService
import com.example.smarthome.service.network.mapper.*
import com.example.smarthome.service.network.model.*

class InformationRepository(
    networkModule: NetworkModule
) {

    private var informationService = networkModule.createService(InformationService::class.java)

    suspend fun getInfo() =
        getAllResponseMapper(informationService.getAll(), getTime())

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
    ) = conditionerResponseMapper(
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