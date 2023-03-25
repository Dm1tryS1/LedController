package com.example.smarthome.service.network

import com.example.smarthome.service.network.model.SystemSettingsRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SystemService {
    @POST("systemsettings")
    suspend fun systemsettings(
        @Body systemSettingsRequest: SystemSettingsRequest
    )
}