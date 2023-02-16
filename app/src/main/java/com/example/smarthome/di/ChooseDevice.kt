package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceViewModel
import com.example.smarthome.repository.FileRepository
import com.example.smarthome.repository.NetworkRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChooseDevice {

    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { parameters -> ChooseDeviceViewModel(get(), get(), byIp = parameters.get()) }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {
        factory { FileRepository(get()) }
        factory { NetworkRepository() }
    }
}