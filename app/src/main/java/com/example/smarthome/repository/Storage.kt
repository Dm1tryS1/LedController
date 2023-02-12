package com.example.smarthome.repository

import android.content.Context

class Storage(context: Context) {

    private val preferences = context.getSharedPreferences(
        sharedPreferenceName,
        Context.MODE_PRIVATE
    )

    fun getUserSettings(key: String): Int = preferences.getInt(key, -1)

    fun saveUserSettings(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    companion object {
        const val sharedPreferenceName = "SmartHomeStorage"
        const val userTimer = "UserTimer"
        const val userMaxTemperature = "UserMaxTemperature"
        const val userMinTemperature = "UserMinTemperature"
        const val userMaxHumidity = "UserMaxHumidity"
        const val userMinHumidity = "UserMinHumidity "
        const val userDisplayedValue = "UserDisplayedValue"
    }
}