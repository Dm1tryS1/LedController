package com.example.smarthome.main

import android.os.Parcelable
import com.example.data.device.ControlType
import com.example.data.device.SensorType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseDeviceParams(val controlType: ControlType = ControlType.Connect) : Parcelable

@Parcelize
data class ChartsParams(val deviceType: SensorType, val id: Int) : Parcelable