package com.example.home_impl

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.home_impl.presentation.HomeFragment
import com.example.home_api.HomeFeature
import com.github.terrakok.cicerone.Screen

class HomeFeatureImpl : HomeFeature {
    override fun createScreen(params: NoParams): Screen =
        HomeFragment::class.java.createScreen(params)
}
