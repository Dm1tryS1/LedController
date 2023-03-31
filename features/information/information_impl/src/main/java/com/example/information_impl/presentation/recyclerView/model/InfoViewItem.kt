package com.example.information_impl.presentation.recyclerView.model

import com.example.data.device.SensorType

sealed class InfoViewItem {
    data class SensorsInfoViewItem(
        val iconId: Int,
        val id: Int,
        val info: String,
        val date: String,
        val sensorType: SensorType,
        var status: Boolean
    ) : InfoViewItem()

    data class Header(
        val type: String
    ) : InfoViewItem()
}

