package com.example.smarthome.fragments.home

import com.example.core.navigation.createScreen
import com.example.core.presentation.BaseViewModel
import com.example.smarthome.fragments.information.InformationFragment
import com.github.terrakok.cicerone.Router

class HomeViewModel(
    router: Router,
) : BaseViewModel<Unit, Unit>(router = router) {

    fun openInformationFragment() {
        router.navigateTo(InformationFragment::class.java.createScreen(null))
    }

    override fun createInitialState() = Unit


}