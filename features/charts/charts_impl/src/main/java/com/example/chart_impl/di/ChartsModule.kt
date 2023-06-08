package com.example.chart_impl.di

import com.example.chart_impl.ChartsFeatureImpl
import com.example.chart_impl.data.DeviceInfoDataBaseRepository
import com.example.chart_impl.domain.ChartUseCase
import com.example.chart_impl.presentation.ChartsViewModel
import com.example.charts_api.ChartsFeature
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object ChartsModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { ChartsFeatureImpl() } bind ChartsFeature::class
    }

    private fun createPresentationModule() = module {
        viewModel { ChartsViewModel(get(), get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { ChartUseCase(get()) }
    }

    private fun createDataModule() = module {
        factory { DeviceInfoDataBaseRepository(get()) }
    }
}