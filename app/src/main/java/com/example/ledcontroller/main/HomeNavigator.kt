package com.example.ledcontroller.main

import androidx.fragment.app.FragmentManager
import com.example.ledcontroller.fragments.home.Home
import com.example.ledcontroller.fragments.information.Information
import com.example.ledcontroller.fragments.settings.Settings
import com.example.ledcontroller.fragments.table.Table

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
            is Screen.Table -> {
                selectFragment(Table())
            }
            is Screen.Information -> {
                selectFragment(Information())
            }
        }
    }
}