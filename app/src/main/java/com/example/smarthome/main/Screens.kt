package com.example.smarthome.main

import com.example.smarthome.fragments.charts.ChartsFragment
import com.example.smarthome.fragments.connectDevice.ConnectDeviceFragment
import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceFragment
import com.example.smarthome.fragments.connectDevice.remoteControl.RemoteControlFragment
import com.example.smarthome.fragments.information.InformationFragment
import com.example.smarthome.fragments.main.MainFragment
import com.example.smarthome.fragments.system.SystemFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun InformationScreen() = FragmentScreen { InformationFragment() }
    fun MainScreen() = FragmentScreen { MainFragment() }
    fun ChartScreen(deviceTypes: Int, id: Int) = FragmentScreen { ChartsFragment.getNewInstance(deviceTypes, id) }
    fun SystemScreen() = FragmentScreen { SystemFragment() }
    fun ConnectDeviceScreen() = FragmentScreen { ConnectDeviceFragment() }
    fun ChooseDeviceScreen(controlType: Int) = FragmentScreen { ChooseDeviceFragment.getNewInstance(controlType) }
    fun RemoteControlScreen() = FragmentScreen { RemoteControlFragment() }
}