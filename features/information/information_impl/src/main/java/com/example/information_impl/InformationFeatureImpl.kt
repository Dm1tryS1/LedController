package com.example.information_impl

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.information_api.InformationFeature
import com.example.information_impl.presentation.InformationFragment
import com.github.terrakok.cicerone.Screen

class InformationFeatureImpl : InformationFeature {
    override fun createScreen(params: NoParams): Screen = InformationFragment::class.java.createScreen(params)
}