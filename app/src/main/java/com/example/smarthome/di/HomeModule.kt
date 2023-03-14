package com.example.smarthome.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.smarthome.fragments.home.HomeViewModel
import org.koin.dsl.module

object HomeModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { HomeViewModel(get()) }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {

    }

}