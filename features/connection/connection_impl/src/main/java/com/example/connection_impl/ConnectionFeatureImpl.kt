package com.example.connection_impl

import ConnectionFeature
import com.example.connection_impl.presentation.connection.ConnectDeviceFragment
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.github.terrakok.cicerone.Screen

class ConnectionFeatureImpl : ConnectionFeature {
    override fun createFeature(params: NoParams): Screen =
        ConnectDeviceFragment::class.java.createScreen(params)
}