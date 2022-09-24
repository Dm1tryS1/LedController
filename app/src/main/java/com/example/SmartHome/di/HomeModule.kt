package com.example.SmartHome.di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.SmartHome.fragments.home.HomeViewModel
import org.koin.dsl.module

object HomeModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { HomeViewModel() }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {

    }

}