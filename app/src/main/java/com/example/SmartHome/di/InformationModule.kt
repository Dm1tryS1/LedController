package com.example.SmartHome.di

import com.example.SmartHome.fragments.information.InformationInteractor
import com.example.SmartHome.fragments.information.InformationViewModel
import com.example.SmartHome.repository.Storage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object InformationModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { InformationViewModel(get()) }
    }

    private fun createDomainModule() = module {
        factory { InformationInteractor(get(), get()) }
    }

    private fun createDataModule() = module {
        factory { Storage(get()) }
    }
}