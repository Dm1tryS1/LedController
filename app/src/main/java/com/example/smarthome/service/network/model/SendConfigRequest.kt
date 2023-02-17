package com.example.smarthome.service.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendConfigRequest(
    @SerialName("ip")
    val ip: String,

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