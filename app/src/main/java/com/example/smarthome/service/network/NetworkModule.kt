package com.example.smarthome.service.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkModule {
    fun createConfigService(systemAddress: String): ConfigService {
        val gson = GsonBuilder().create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).retryOnConnectionFailure(true)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://$systemAddress/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ConfigService::class.java)
    }
}