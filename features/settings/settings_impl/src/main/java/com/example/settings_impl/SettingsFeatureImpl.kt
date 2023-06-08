package com.example.settings_impl

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.settings_api.SettingsFeature
import com.example.settings_impl.presentation.SettingsFragment

class SettingsFeatureImpl: SettingsFeature {
    override fun createScreen(params: NoParams) = SettingsFragment::class.java.createScreen(params)
}