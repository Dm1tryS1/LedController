package com.example.system_impl.di

import com.example.system_api.SystemFeature
import com.example.system_impl.SystemFeatureImpl
import com.example.system_impl.data.SharedPreferencesRepository
import com.example.system_impl.data.SystemRepository
import com.example.system_impl.domain.SystemUseCase
import com.example.system_impl.presentation.SystemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object SystemModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { SystemFeatureImpl() } bind SystemFeature::class
    }

    private fun createPresentationModule() = module {
        viewModel { SystemViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { SystemUseCase(get(), get()) }
    }

    private fun createDataModule() = module {
        factory { SystemRepository(get()) }
        factory { SharedPreferencesRepository(get()) }
    }
}