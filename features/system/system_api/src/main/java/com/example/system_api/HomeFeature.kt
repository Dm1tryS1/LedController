package com.example.system_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface SystemFeature {
    fun createScreen(params: NoParams): Screen
}