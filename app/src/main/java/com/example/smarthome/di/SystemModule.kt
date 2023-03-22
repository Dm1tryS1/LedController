package com.example.smarthome.di

import com.example.smarthome.fragments.system.SystemUseCase
import com.example.smarthome.fragments.system.SystemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SystemModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { SystemViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { SystemUseCase(get(), get()) }
    }

    private fun createDataModule() = module {
    }
}