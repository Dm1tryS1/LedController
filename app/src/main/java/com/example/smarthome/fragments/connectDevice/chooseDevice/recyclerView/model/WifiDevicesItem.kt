package com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WifiDevicesItem(
    @SerialName("brand")
    val brand: String,

    @SerialName("name")
    val name: String,

    @SerialName("id")
    val id: Int,

    @SerialName("deviceType")
    val deviceType: Int,

    @SerialName("commands")
    val commands: HashMap<String, String>
)
