package com.example.smarthome.repository

import com.example.shared_preferences.SharedPreferencesService

class SharedPreferencesRepository(private val sharedPreferencesModule: SharedPreferencesService) {

    fun getString(key: String) = sharedPreferencesModule.getString(key)

    fun saveString(key: String, value: String) = sharedPreferencesModule.saveString(key, value)
}