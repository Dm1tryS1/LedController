package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.ConnectDeviceInteractor
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
        factory { ConnectDeviceInteractor(get(), get(), get(), get()) }
    }

    private fun createDataModule() = module {
        factory { WifiDeviceRepository(get()) }
    }
}