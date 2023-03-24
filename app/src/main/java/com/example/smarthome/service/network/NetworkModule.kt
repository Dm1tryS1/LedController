package com.example.smarthome.service.network

import com.example.smarthome.repository.SharedPreferencesRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkModule(sharedPreferencesRepository: SharedPreferencesRepository) {

    private val ip = (sharedPreferencesRepository.getString(SharedPreferencesRepository.systemIp)
        ?: "").ifEmpty { "192.168.1.30" }

    fun <T> createService(type: Class<T>): T {
        val gson = GsonBuilder().create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://${ip}/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(type)

    }

}