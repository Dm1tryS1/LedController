package com.example.smarthome.di

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
        viewModel { SystemViewModel(get()) }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {
    }
}