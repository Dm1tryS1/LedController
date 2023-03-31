package com.example.information_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface InformationFeature {
    fun createScreen(params: NoParams): Screen
}