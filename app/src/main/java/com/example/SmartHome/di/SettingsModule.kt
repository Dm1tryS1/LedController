package com.example.SmartHome.di

import com.example.SmartHome.fragments.settings.DevicesUseCase
import com.example.SmartHome.fragments.settings.SettingsViewModel
import com.example.SmartHome.repository.DeviceRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SettingsModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { SettingsViewModel(get()) }
    }

    private fun createDomainModule() = module {
        factory { DevicesUseCase(get(), get()) }
    }

    private fun createDataModule() = module {
        single { DeviceRepository(get()) }
    }

}