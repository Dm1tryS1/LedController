package com.example.ledcontroller

import android.app.Application
import com.example.ledcontroller.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class LedControllerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            allowOverride(false)
            androidContext(this@LedControllerApplication)
            modules(AppModule())
        }
    }
}