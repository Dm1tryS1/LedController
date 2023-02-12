package com.example.smarthome.fragments.system

import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router

class SystemViewModel(private val router: Router) : BaseViewModel<Unit, Unit>() {

    override fun createInitialState() = Unit

    fun save() {
        router.backTo(Screens.InformationScreen())
    }

}