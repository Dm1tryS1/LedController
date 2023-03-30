package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceUseCase
import com.example.smarthome.fragments.connectDevice.ConnectDeviceViewModel
import com.example.smarthome.repository.ConnectDeviceRepository
import com.example.smarthome.repository.FileRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ConnectDeviceModule {

    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { ConnectDeviceViewModel(get()) }
    }

    private fun createDomainModule() = module {
        factory { ChooseDeviceUseCase(get(), get(), get()) }
    }

    private fun createDataModule() = module {
        factory { FileRepository(get()) }
        factory { ConnectDeviceRepository(get()) }
    }
}