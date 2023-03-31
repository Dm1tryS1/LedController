package com.example.system_impl.data

import com.example.shared_preferences.SharedPreferencesService

class SharedPreferencesRepository(private val sharedPreferencesModule: SharedPreferencesService) {

    fun getInt(key: String) = sharedPreferencesModule.getInt(key)

    fun saveInt(key: String, value: Int) = sharedPreferencesModule.saveInt(key, value)

    fun deleteUserSettings(key: String) = sharedPreferencesModule.deleteUserSettings(key)
}