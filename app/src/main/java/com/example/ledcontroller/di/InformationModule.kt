package com.example.ledcontroller.di

import com.example.ledcontroller.fragments.information.GetInfoUseCase
import com.example.ledcontroller.fragments.information.InformationViewModel
import com.example.ledcontroller.fragments.table.DataUseCase
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
        factory { GetInfoUseCase(get()) }
    }

    private fun createDataModule() = module {

    }
}