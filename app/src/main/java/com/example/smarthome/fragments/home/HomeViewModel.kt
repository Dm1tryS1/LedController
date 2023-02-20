package com.example.smarthome.fragments.home

import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class HomeViewModel(
    val homeInteractor: HomeInteractor,
    private val router: Router,
) : BaseViewModel<Unit, HomeEvent>() {

    fun checkConnection() {
        if (homeInteractor.checkConnection())
            router.navigateTo(Screens.InformationScreen())
        else
            sendEvent(HomeEvent.NoConnectionEvent)
    }

    override fun createInitialState() = Unit


}