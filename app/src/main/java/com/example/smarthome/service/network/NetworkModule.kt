package com.example.smarthome.service.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    fun createConfigService(systemAddress: String): ConfigService {
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
           // .baseUrl("http://$systemAddress/") TODO заменить на IP адрес wi-fi модуля
            .baseUrl("https://192.168.1.33/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ConfigService::class.java)
    }
}