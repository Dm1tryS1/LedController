package com.example.smarthome.di

import com.example.smarthome.fragments.charts.ChartsViewModel
import com.example.smarthome.fragments.charts.formatter.ChartInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChartsModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { ChartsViewModel(get()) }
    }

    private fun createDomainModule() = module {
        factory { ChartInteractor(get()) }
    }

    private fun createDataModule() = module {

    }
}