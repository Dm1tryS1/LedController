package com.example.smarthome.service.network

import com.example.smarthome.repository.model.BaseResponse
import com.example.smarthome.service.network.model.SendConfigRequest
import com.example.smarthome.service.network.model.SendConfigResponse
import retrofit2.http.*

interface ConfigService {
    @Headers("Content-Type: application/json")
    @POST("setConfig")
    suspend fun sendConfig(
        @Body devicesConfigs: List<SendConfigRequest>
    ): BaseResponse<SendConfigResponse>
}