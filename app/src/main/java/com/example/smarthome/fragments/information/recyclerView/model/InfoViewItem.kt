package com.example.smarthome.fragments.information.recyclerView.model

import com.example.smarthome.common.device.SensorType

sealed class InfoViewItem {
    data class SensorsInfoViewItem(val iconId: Int, val id: Int, val info: String, val date: String, val sensorType: SensorType) : InfoViewItem()
    data class Header(
        val type: String
    ) : InfoViewItem()
}

