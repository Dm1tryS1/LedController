package com.example.ledcontroller.main

import androidx.fragment.app.FragmentManager
import com.example.ledcontroller.fragments.home.Home
import com.example.ledcontroller.fragments.settings.Settings

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
        }
    }
}