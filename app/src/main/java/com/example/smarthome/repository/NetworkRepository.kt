package com.example.smarthome.repository

import android.util.Log
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.smarthome.repository.model.BaseResponse
import com.example.smarthome.service.network.model.SendConfigResponse
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.model.SendConfigRequest

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>,
        systemIp: String,
    ): BaseResponse<SendConfigResponse> {
        //networkModule.createConfigService(systemIp).sendConfig(wifiDevicesItem) TODO заменить для реального устройства
        wifiDevicesItem.map { Log.d("12345", it.toString()) }
        return BaseResponse<SendConfigResponse>(
            null,
            SendConfigResponse(wifiDevicesItem.map { it.id })
        )
    }

}