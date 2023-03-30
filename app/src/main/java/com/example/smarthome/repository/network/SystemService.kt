package com.example.smarthome.repository.network

import com.example.smarthome.repository.network.model.SystemSettingsRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SystemService {
    @POST("systemsettings")
    suspend fun systemsettings(
        @Body systemSettingsRequest: SystemSettingsRequest
    )
}