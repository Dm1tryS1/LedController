package com.example.home_impl.di

import com.example.home_api.HomeFeature
import com.example.home_impl.HomeFeatureImpl
import com.example.home_impl.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object HomeModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { HomeFeatureImpl() } bind HomeFeature::class
    }

    private fun createPresentationModule() = module {
        viewModel { HomeViewModel(get(), get()) }

        factory { HomeViewModel.Features(get()) }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {

    }

}