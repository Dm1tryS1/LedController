package com.example.smarthome.service.network

import com.example.smarthome.service.network.model.SendConfigRequest
import retrofit2.Call
import retrofit2.http.*

interface ConfigService {

    @POST("sendconfig")
    suspend fun sendConfig(
        @Body wifiDevicesItem: List<SendConfigRequest>
    ): Call<Unit>
}