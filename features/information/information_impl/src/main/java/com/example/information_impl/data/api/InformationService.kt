package com.example.information_impl.data.api

import com.example.information_impl.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface InformationService {

    @GET("temperature")
    suspend fun temperature(@Query("id") id: Int
    ): TemperatureResponse

    @GET("pressure")
    suspend fun pressure(@Query("id") id: Int
    ): PressureResponse

    @GET("humidity")
    suspend fun humidity(@Query("id") id: Int
    ): HumidityResponse

    @GET("getall")
    suspend fun getAll(
    ): GetAllResponse

    @POST("condcommand")
    suspend fun condcommand(
        @Body command: ConditionerRequest
    ): ConditionerResponse

    @POST("humcommand")
    suspend fun humcommand(
        @Body command: HumidifierRequest
    ): HumidifierResponse

    @POST("systemtimer")
    suspend fun systemtimer(
        @Body timer: SystemTimerRequest
    )
}