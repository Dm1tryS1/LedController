package com.example.network

import com.example.shared_preferences.SharedPreferences
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkFactory(sharedPreferences: SharedPreferences) {

    private val ip = (sharedPreferences.getString(SharedPreferences.systemIp)
        ?: "").ifEmpty { "192.168.1.30" }

    fun <T> createService(type: Class<T>): T {
        val gson = GsonBuilder().create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://${ip}/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(type)

    }

}