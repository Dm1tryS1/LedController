package com.example.SmartHome.repository

import android.content.Context

class Storage(context: Context) {

    private val preferences = context.getSharedPreferences(
        sharedPreferenceName,
        Context.MODE_PRIVATE
    )

    fun getUserSettings(callback: (value: Int) -> Unit) {
        callback(preferences.getInt(incognitoModeString, 0))
    }

    fun saveUserSettings(value: Int) {
        val editor = preferences.edit()
        editor.putInt(incognitoModeString, value)
        editor.apply()
    }

    companion object {
        const val sharedPreferenceName = "SmartHomeStorage"
        const val incognitoModeString = "UserSettings"
    }
}