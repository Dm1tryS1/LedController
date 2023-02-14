package com.example.smarthome.utils

import com.example.smarthome.fragments.charts.Charts
import com.example.smarthome.fragments.connectDevice.ConnectDevice
import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDevice
import com.example.smarthome.fragments.home.Home
import com.example.smarthome.fragments.information.Information
import com.example.smarthome.fragments.main.Main
import com.example.smarthome.fragments.settings.Settings
import com.example.smarthome.fragments.system.System
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun HomeScreen() = FragmentScreen { Home() }
    fun SettingsScreen() = FragmentScreen { Settings() }
    fun InformationScreen() = FragmentScreen { Information() }
    fun MainScreen() = FragmentScreen { Main() }
    fun ChartScreen() = FragmentScreen { Charts() }
    fun SystemScreen() = FragmentScreen { System() }
    fun ConnectDeviceScreen() = FragmentScreen { ConnectDevice() }

    fun ChooseDeviceScreen() = FragmentScreen { ChooseDevice() }
}