package com.example.smarthome.di

import com.example.smarthome.fragments.charts.ChartsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChartsModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { ChartsViewModel() }
    }

    private fun createDomainModule() = module {

    }

    private fun createDataModule() = module {

    }
}