package com.example.smarthome.di

import com.example.smarthome.fragments.information.InformationInteractor
import com.example.smarthome.fragments.information.InformationViewModel
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
        factory { InformationInteractor(get(), get()) }
    }

    private fun createDataModule() = module {
        factory { SharedPreferencesRepository(get()) }
    }
}