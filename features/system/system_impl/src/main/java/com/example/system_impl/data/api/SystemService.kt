package com.example.system_impl.data.api

import com.example.system_impl.data.model.SystemSettingsRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SystemService {
    @POST("systemsettings")
    suspend fun systemsettings(
        @Body systemSettingsRequest: SystemSettingsRequest
    )
}