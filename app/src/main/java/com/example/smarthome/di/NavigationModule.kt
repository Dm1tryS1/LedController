package com.example.smarthome.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

import org.koin.dsl.module

object NavigationModule {

    operator fun invoke() = listOf(
        createNavigation(),
    )

    private fun createNavigation() = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
        single { get<Cicerone<Router>>().router }
    }
}