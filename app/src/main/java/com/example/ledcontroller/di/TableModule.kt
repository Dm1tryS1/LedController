package com.example.ledcontroller.di

import com.example.ledcontroller.fragments.table.TableViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object TableModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { TableViewModel(get()) }
    }

    private fun createDomainModule() = module {

    }

    private fun createDataModule() = module {

    }
}