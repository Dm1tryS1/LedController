package com.example.smarthome.fragments.home

import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router

class HomeViewModel(val homeInteractor: HomeInteractor, private val router: Router) : BaseViewModel() {

    fun checkConnection() : Boolean {
        val isConnected = homeInteractor.checkConnection()
        if (isConnected)
            router.navigateTo(Screens.InformationScreen())
        return isConnected
    }
}