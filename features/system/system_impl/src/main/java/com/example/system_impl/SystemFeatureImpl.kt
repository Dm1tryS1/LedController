package com.example.system_impl

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.system_api.SystemFeature
import com.example.system_impl.presentation.SystemFragment
import com.github.terrakok.cicerone.Screen

class SystemFeatureImpl : SystemFeature {
    override fun createScreen(params: NoParams): Screen = SystemFragment::class.java.createScreen(NoParams)
}