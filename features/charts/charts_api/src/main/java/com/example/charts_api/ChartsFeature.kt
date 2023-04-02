package com.example.charts_api

import android.os.Parcelable
import com.example.data.device.SensorType
import com.github.terrakok.cicerone.Screen
import kotlinx.parcelize.Parcelize

interface ChartsFeature {
    fun createScreen(params: ChartsParams): Screen

    @Parcelize
    data class ChartsParams(val deviceType: SensorType, val id: Int) : Parcelable
}