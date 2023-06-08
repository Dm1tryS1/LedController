package com.example.network.di

import com.example.network.NetworkFactory
import org.koin.dsl.module

object NetworkModule {
    operator fun invoke() = listOf(
        createNetworkModule()
    )

    private fun createNetworkModule() = module {
        factory { NetworkFactory(get()) }
    }
}