package com.example.smarthome.utils

import com.example.smarthome.fragments.home.Home
import com.example.smarthome.fragments.information.Information
import com.example.smarthome.fragments.settings.Settings

import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun HomeScreen() = FragmentScreen { Home() }
    fun SettingsScreen() = FragmentScreen { Settings() }
    fun InformationScreen() = FragmentScreen { Information() }
}