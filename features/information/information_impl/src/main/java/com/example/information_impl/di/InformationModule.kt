package com.example.information_impl.di

import com.example.information_api.InformationFeature
import com.example.information_impl.InformationFeatureImpl
import com.example.information_impl.data.DeviceInfoDataBaseRepository
import com.example.information_impl.data.InformationRepository
import com.example.information_impl.data.SharedPreferencesRepository
import com.example.information_impl.domain.InformationUseCase
import com.example.information_impl.presentation.InformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object InformationModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { InformationFeatureImpl() } bind InformationFeature::class
    }

    private fun createPresentationModule() = module {
        viewModel { InformationViewModel(get(), get(), get()) }

        factory { InformationViewModel.Features(get()) }
    }

    private fun createDomainModule() = module {
        factory { InformationUseCase(get(), get(), get()) }
    }

    private fun createDataModule() = module {
        factory { InformationRepository(get()) }
        factory { DeviceInfoDataBaseRepository(get()) }
        factory { SharedPreferencesRepository(get()) }
    }
}