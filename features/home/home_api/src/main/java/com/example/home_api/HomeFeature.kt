package com.example.home_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface HomeFeature {
    fun createScreen(params: NoParams): Screen
}