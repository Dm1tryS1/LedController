package com.example.smarthome.fragments.home

import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class HomeViewModel(
    private val router: Router,
) : BaseViewModel<Unit, Unit>() {

    fun openInformationFragment() {
        router.navigateTo(Screens.InformationScreen())
    }

    override fun createInitialState() = Unit


}