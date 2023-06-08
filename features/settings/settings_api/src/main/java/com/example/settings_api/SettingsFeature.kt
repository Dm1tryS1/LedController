package com.example.settings_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface SettingsFeature {
    fun createScreen(params: NoParams) : Screen
}