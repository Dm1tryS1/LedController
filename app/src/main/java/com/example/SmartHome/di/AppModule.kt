package com.example.SmartHome.di

import org.koin.core.module.Module

object AppModule {
    operator fun invoke() = listOf(
        createModules()
    ).flatten()

    private fun createModules(): List<Module> = listOf(
        HomeModule(),
        SettingsModule(),
        InformationModule()
    ).flatten()
}