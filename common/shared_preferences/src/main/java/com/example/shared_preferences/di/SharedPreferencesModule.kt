package com.example.shared_preferences.di

import com.example.shared_preferences.SharedPreferences
import org.koin.dsl.module

object SharedPreferencesModule {
    operator fun invoke() = listOf(
        createSharedPreferencesModule()
    )

    private fun createSharedPreferencesModule() = module {
        factory { SharedPreferences(get()) }
    }
}