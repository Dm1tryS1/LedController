package com.example.smarthome.repository

import com.example.shared_preferences.SharedPreferencesService

class SharedPreferencesRepository(private val sharedPreferencesModule: SharedPreferencesService) {

    fun getInt(key: String) = sharedPreferencesModule.getInt(key)

    fun getString(key: String) = sharedPreferencesModule.getString(key)

    fun saveInt(key: String, value: Int) = sharedPreferencesModule.saveInt(key, value)

    fun saveString(key: String, value: String) = sharedPreferencesModule.saveString(key, value)

    fun deleteUserSettings(key: String) = sharedPreferencesModule.deleteUserSettings(key)
}