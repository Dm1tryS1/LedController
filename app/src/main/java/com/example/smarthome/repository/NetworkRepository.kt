package com.example.smarthome.repository

import com.example.smarthome.common.getTime
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.mapper.*
import com.example.smarthome.service.network.model.*

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>
    ) = networkModule.createService().sendConfig(wifiDevicesItem)


    suspend fun getInfo() =
        getAllResponseMapper(networkModule.createService().getAll(), getTime())

    suspend fun getTemperature(
        id: Int
    ) = temperatureResponseMapper(networkModule.createService().temperature(id), getTime())


    suspend fun getPressure(
        id: Int
    ) = pressureResponseMapper(networkModule.createService().pressure(id), getTime())


    suspend fun getHumidity(
        id: Int
    ) = humidityResponseMapper(networkModule.createService().humidity(id), getTime())


    suspend fun condCommand(
        command: String
    ) = conditionerResponseMapper(
        networkModule.createService().condcommand(ConditionerRequest(command)), getTime()
    )

    suspend fun humCommand(
        command: String
    ) = humidifierResponseMapper(
        networkModule.createService().humcommand(HumidifierRequest(command)),
        getTime()
    )

    suspend fun setTimer(
        value: Int
    ) =
        networkModule.createService().systemtimer(SystemTimerRequest(value))


    suspend fun setSystemSettings(
        minTemp: Int,
        maxTemp: Int,
        minHum: Int,
        maxHum: Int,
        displayedValue: Int
    ) =
        networkModule.createService().systemsettings(
            SystemSettingsRequest(
                minHum = minHum,
                maxHum = maxHum,
                minTemp = minTemp,
                maxTemp = maxTemp,
                displayedValue = displayedValue
            )
        )


    suspend fun writeCommandForRemoteControl(deviceType: Int, command: String) =
        networkModule.createService()
            .irreceiver(IrReceiverRequest(command, deviceType))

}