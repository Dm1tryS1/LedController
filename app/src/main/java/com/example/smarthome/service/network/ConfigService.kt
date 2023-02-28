package com.example.smarthome.service.network

import com.example.smarthome.service.network.model.GetAllResponse
import com.example.smarthome.service.network.model.SendConfigRequest
import com.example.smarthome.service.network.model.SendConfigResponse
import com.example.smarthome.service.network.model.TemperatureResponse
import retrofit2.Call
import retrofit2.http.*

interface ConfigService {

    @POST("sendconfig")
    suspend fun sendConfig(
        @Body wifiDevicesItem: List<SendConfigRequest>
    ): SendConfigResponse

    @GET("temperature")
    suspend fun temperature(
    ): TemperatureResponse

    @GET("getall")
    suspend fun getAll(
    ): GetAllResponse
}