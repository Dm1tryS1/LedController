package com.example.ledcontroller.di

import org.koin.core.module.Module

object AppModule {
    operator fun invoke() = listOf(
        createFeatureModules()
    ).flatten()

    private fun createFeatureModules(): List<Module> = listOf(
        HomeModule(),
        SettingsModule()
    ).flatten()
}