package com.example.smarthome.di

import com.example.core.navigation.NavigationModule
import com.example.home_impl.di.HomeModule
import com.example.information_impl.di.InformationModule
import com.example.network.di.NetworkModule
import com.example.shared_preferences.di.SharedPreferencesModule
import com.example.storage.di.StorageModule
import com.example.system_impl.di.SystemModule
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
        ConnectDeviceModule(),
        ChooseDeviceModule(),
        RemoteControlModule(),
        NetworkModule(),
        SharedPreferencesModule(),
        StorageModule()
    ).flatten()
}