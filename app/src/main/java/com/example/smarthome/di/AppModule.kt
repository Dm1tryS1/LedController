package com.example.smarthome.di

import com.example.network.di.NetworkModule
import com.example.shared_preferences.di.SharedPreferencesModule
import com.example.storage.di.StorageModule
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
        MainModule(),
        ChartsModule(),
        SystemModule(),
        ConnectDevice(),
        ChooseDevice(),
        RemoteControlModule(),
        NetworkModule(),
        SharedPreferencesModule(),
        StorageModule()
    ).flatten()
}