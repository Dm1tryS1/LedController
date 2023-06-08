package com.example.chart_impl

import com.example.chart_impl.presentation.ChartsFragment
import com.example.charts_api.ChartsFeature
import com.example.core.navigation.createScreen

class ChartsFeatureImpl : ChartsFeature {
    override fun createScreen(params: ChartsFeature.ChartsParams) =
        ChartsFragment::class.java.createScreen(params)
}