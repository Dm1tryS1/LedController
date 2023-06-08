package com.example.storage.di

import com.example.storage.DeviceInfoDataBaseProvider
import org.koin.dsl.module

object StorageModule {
    operator fun invoke() = listOf(
        createStorageModule()
    )

    private fun createStorageModule() = module {
        factory { DeviceInfoDataBaseProvider(get()) }
    }
}