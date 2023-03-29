package com.example.smarthome.fragments.home

import com.example.core.presentation.BaseViewModel
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router

class HomeViewModel(
    router: Router,
) : BaseViewModel<Unit, Unit>(router = router) {

    fun openInformationFragment() {
        router.navigateTo(Screens.informationScreen())
    }

    override fun createInitialState() = Unit


}