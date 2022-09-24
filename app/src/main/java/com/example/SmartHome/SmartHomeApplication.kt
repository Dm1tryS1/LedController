package com.example.smarthome

import android.app.Application
import com.example.smarthome.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class SmartHomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            allowOverride(false)
            androidContext(this@SmartHomeApplication)
            modules(AppModule())
        }
    }
}