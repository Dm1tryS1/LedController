package com.example.connection_impl

import ConnectionFeature
import com.example.connection_impl.domain.ChooseDeviceUseCase
import com.example.connection_impl.presentation.connection.ConnectDeviceFragment
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.data.wifi.WifiInfo
import com.github.terrakok.cicerone.Screen

class ConnectionFeatureImpl(
    private val deviceUseCase: ChooseDeviceUseCase
) : ConnectionFeature {
    override fun createFeature(params: NoParams): Screen =
        ConnectDeviceFragment::class.java.createScreen(params)

    override suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        deviceUseCase.connect(wifiInfo) { callback(it) }
    }

    override fun getWifiInfo(): WifiInfo? = deviceUseCase.getWifiInfo()
}