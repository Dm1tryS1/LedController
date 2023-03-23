package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.ConnectDeviceUseCase
import com.example.smarthome.fragments.connectDevice.ConnectDeviceViewModel
import com.example.smarthome.repository.WifiDeviceRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ConnectDevice {

    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { ConnectDeviceViewModel(get()) }
    }

    private fun createDomainModule() = module {
        factory { ConnectDeviceUseCase(get(), get(), get()) }
    }

    private fun createDataModule() = module {
        factory { WifiDeviceRepository(get()) }
    }
}