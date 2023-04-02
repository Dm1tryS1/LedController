package com.example.connection_impl.di

import ConnectionFeature
import com.example.connection_impl.ConnectionFeatureImpl
import com.example.connection_impl.data.ConnectDeviceRepository
import com.example.connection_impl.data.FileRepository
import com.example.connection_impl.data.WifiDeviceRepository
import com.example.connection_impl.domain.ChooseDeviceUseCase
import com.example.connection_impl.domain.RemoteControlUseCase
import com.example.connection_impl.presentation.choose_device.ChooseDeviceViewModel
import com.example.connection_impl.presentation.connection.ConnectDeviceViewModel
import com.example.connection_impl.presentation.remote_control.RemoteControlViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object ConnectionModule {

    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { ConnectionFeatureImpl() } bind ConnectionFeature::class
    }

    private fun createPresentationModule() = module {
        viewModel { RemoteControlViewModel(get(), get()) }
        viewModel { ConnectDeviceViewModel(get()) }
        viewModel { ChooseDeviceViewModel(get(), get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { ChooseDeviceUseCase(get(), get(), get()) }
        factory { RemoteControlUseCase(get()) }
    }

    private fun createDataModule() = module {
        factory { FileRepository(get()) }
        factory { ConnectDeviceRepository(get()) }
        factory { WifiDeviceRepository(get()) }
    }
}