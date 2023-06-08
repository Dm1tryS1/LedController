package com.example.settings_impl.presentation.domain

import com.example.shared_preferences.SharedPreferences

class SettingsUseCase(
    private val sharedPreferencesRepository: SharedPreferences,
) {
    fun saveSystemIp(ip: String) =
        sharedPreferencesRepository.saveString(SharedPreferences.systemIp, ip)

}