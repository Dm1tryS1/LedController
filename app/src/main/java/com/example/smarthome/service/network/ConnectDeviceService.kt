package com.example.smarthome.service.network

import com.example.smarthome.service.network.model.IrReceiverRequest
import com.example.smarthome.service.network.model.IrReceiverResponse
import com.example.smarthome.service.network.model.SendConfigRequest
import com.example.smarthome.service.network.model.SendConfigResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ConnectDeviceService {
    @POST("sendconfig")
    suspend fun sendConfig(
        @Body wifiDevicesItem: List<SendConfigRequest>
    ): SendConfigResponse

    @POST("irreceiver")
    suspend fun irreceiver(
        @Body irReceiverRequest: IrReceiverRequest
    ): IrReceiverResponse
}