package com.example.smarthome.fragments.information.recyclerView.model

import android.os.Parcelable
import com.example.smarthome.utils.SensorType
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoViewItem(val iconId: Int, val id: Int, val info: String, val date: String, val sensorType: SensorType) : Parcelable

