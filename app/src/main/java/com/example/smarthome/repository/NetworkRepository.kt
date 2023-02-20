package com.example.smarthome.repository

import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.model.SendConfigRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>,
        systemIp: String,
        callback: (success: Boolean) -> Unit
    ) {
        networkModule.createConfigService(systemIp).sendConfig(wifiDevicesItem).enqueue(
            object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    callback(true)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback(false)
                }

            }
        )

    }
}