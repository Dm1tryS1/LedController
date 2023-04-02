package com.example.settings_impl.presentation.domain

import com.example.shared_preferences.SharedPreferencesService

class SettingsUseCase(
    private val sharedPreferencesRepository: SharedPreferencesService,
) {
    fun saveSystemIp(ip: String) =
        sharedPreferencesRepository.saveString(SharedPreferencesService.systemIp, ip)

}