package com.example.ledcontroller.di

import com.example.ledcontroller.fragments.home.DataUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.ledcontroller.fragments.home.HomeViewModel
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
        factory { DataUseCase(get()) }
    }

    private fun createDataModule() = module {

    }

}