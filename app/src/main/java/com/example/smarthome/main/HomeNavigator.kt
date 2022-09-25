package com.example.smarthome.main

import androidx.fragment.app.FragmentManager
import com.example.smarthome.fragments.home.Home
import com.example.smarthome.fragments.information.Information
import com.example.smarthome.fragments.settings.Settings

open class HomeNavigator(fragmentManager: FragmentManager, containerId: Int) :
    Navigator(fragmentManager, containerId) {

    override fun setScreen(screen: Screen) {
        when (screen) {
            is Screen.Home -> {
                selectFragment(Home())
            }
            is Screen.Settings -> {
                selectFragment(Settings())
            }
            is Screen.Information -> {
                selectFragment(Information())
            }
        }
    }
}