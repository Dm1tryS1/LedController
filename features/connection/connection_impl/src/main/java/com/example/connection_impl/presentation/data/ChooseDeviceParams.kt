package com.example.connection_impl.presentation.data

import android.os.Parcelable
import com.example.data.device.ControlType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseDeviceParams(val controlType: ControlType = ControlType.Connect) : Parcelable