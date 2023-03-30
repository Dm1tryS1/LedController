package com.example.smarthome.di

import com.example.smarthome.fragments.information.InformationUseCase
import com.example.smarthome.fragments.information.InformationViewModel
import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.InformationRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object InformationModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { InformationViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { InformationUseCase(get(), get(), get()) }
    }

    private fun createDataModule() = module {
        factory { InformationRepository(get()) }
        factory { DeviceInfoDataBaseRepository(get()) }
        factory { SharedPreferencesRepository(get()) }
    }
}