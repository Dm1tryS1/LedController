package com.example.smarthome.service.network

import com.example.smarthome.service.network.model.*
import retrofit2.http.*

interface ConfigService {

    @POST("sendconfig")
    suspend fun sendConfig(
        @Body wifiDevicesItem: List<SendConfigRequest>
    ): SendConfigResponse

    @GET("temperature")
    suspend fun temperature(
    ): TemperatureResponse

    @GET("pressure")
    suspend fun pressure(
    ): PressureResponse

    @GET("humidity")
    suspend fun humidity(
    ): HumidityResponse

    @GET("getall")
    suspend fun getAll(
    ): GetAllResponse

    @POST("condcommand")
    suspend fun condcommand(
        @Body command: ConditionerRequest
    ): ConditionerResponse

    @POST("humcommand")
    suspend fun humcommand(
        @Body command: HumidifierRequest
    ): HumidifierResponse

    @POST("systemtimer")
    suspend fun systemtimer(
        @Body timer: SystemTimerRequest
    )

    @POST("systemsettings")
    suspend fun systemsettings(
        @Body systemSettingsRequest: SystemSettingsRequest
    )

    @POST("irreceiver")
    suspend fun irreceiver(
        @Body irReceiverRequest: IrReceiverRequest
    ): IrReceiverResponse
}