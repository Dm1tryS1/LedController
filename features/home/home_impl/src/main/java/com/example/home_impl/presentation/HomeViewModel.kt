package com.example.home_impl.presentation

import com.example.core.navigation.NoParams
import com.example.core.presentation.BaseViewModel
import com.example.information_api.InformationFeature

import com.github.terrakok.cicerone.Router

class HomeViewModel(
    router: Router,
    private val features: Features,
) : BaseViewModel<Unit, Unit>(router = router) {

    class Features(
        val informationFeature: InformationFeature,
    )

    fun openInformationFragment() {
       router.navigateTo(features.informationFeature.createScreen(NoParams))
    }

    override fun createInitialState() = Unit


}