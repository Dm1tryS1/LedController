package com.example.smarthome.service.network

import com.example.smarthome.repository.SharedPreferencesRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkModule(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun createService(): Service {
        val gson = GsonBuilder().create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://${getIpAddress()}/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(Service::class.java)

    }

    private fun getIpAddress() =
        sharedPreferencesRepository.getString(SharedPreferencesRepository.systemIp) ?: ""

}