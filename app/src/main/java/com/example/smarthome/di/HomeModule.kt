package com.example.smarthome.di

import com.example.smarthome.fragments.home.HomeInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.smarthome.fragments.home.HomeViewModel
import com.example.smarthome.fragments.settings.DevicesUseCase
import org.koin.dsl.module

object HomeModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { HomeViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { HomeInteractor(get()) }
    }

    private fun createDataModule() = module {

    }

}