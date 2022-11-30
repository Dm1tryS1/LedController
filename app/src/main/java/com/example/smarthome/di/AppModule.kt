package com.example.smarthome.di

import org.koin.core.module.Module

object AppModule {
    operator fun invoke() = listOf(
        createModules()
    ).flatten()

    private fun createModules(): List<Module> = listOf(
        HomeModule(),
        SettingsModule(),
        InformationModule(),
        NavigationModule(),
        MainModule()
    ).flatten()
}