package com.example.ledcontroller.di

import com.example.ledcontroller.fragments.information.InformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object InformationModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { InformationViewModel() }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {

    }
}