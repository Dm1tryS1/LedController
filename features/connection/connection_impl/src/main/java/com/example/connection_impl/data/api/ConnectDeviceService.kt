package com.example.connection_impl.data.api

import retrofit2.http.Body
import retrofit2.http.POST

interface ConnectDeviceService {
    @POST("sendconfig")
    suspend fun sendConfig(
        @Body wifiDevicesItem: List<com.example.connection_impl.data.model.SendConfigRequest>
    ): com.example.connection_impl.data.model.SendConfigResponse

    @POST("irreceiver")
    suspend fun irreceiver(
        @Body irReceiverRequest: com.example.connection_impl.data.model.IrReceiverRequest
    ): com.example.connection_impl.data.model.IrReceiverResponse
}