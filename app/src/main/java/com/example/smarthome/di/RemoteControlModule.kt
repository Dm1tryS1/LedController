package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.remoteControl.RemoteControlInteractor
import com.example.smarthome.fragments.connectDevice.remoteControl.RemoteControlViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object RemoteControlModule {

    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { RemoteControlViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { RemoteControlInteractor(get(), get()) }
    }

    private fun createDataModule() = module {
    }
}