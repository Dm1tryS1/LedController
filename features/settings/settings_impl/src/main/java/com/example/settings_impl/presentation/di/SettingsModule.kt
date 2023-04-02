package com.example.settings_impl.presentation.di


import com.example.settings_api.SettingsFeature
import com.example.settings_impl.SettingsFeatureImpl
import com.example.settings_impl.presentation.domain.SettingsUseCase
import com.example.settings_impl.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object SettingsModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { SettingsFeatureImpl() } bind SettingsFeature::class
    }


    private fun createPresentationModule() = module {
        viewModel { SettingsViewModel(get(), get(), get()) }

        factory { SettingsViewModel.Features(get()) }
    }

    private fun createDomainModule() = module {
        factory { SettingsUseCase(get()) }
    }

    private fun createDataModule() = module {
    }

}