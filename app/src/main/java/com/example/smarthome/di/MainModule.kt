package com.example.smarthome.di

import com.example.smarthome.fragments.home.HomeInteractor
import com.example.smarthome.fragments.home.HomeViewModel
import com.example.smarthome.fragments.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { MainViewModel() }
    }

    private fun createDomainModule() = module {

    }

    private fun createDataModule() = module {

    }

}