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
}