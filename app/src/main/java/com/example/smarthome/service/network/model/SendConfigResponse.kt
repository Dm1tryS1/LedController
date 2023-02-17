package com.example.smarthome.service.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendConfigResponse(
    @SerialName("connectedDevices")
    val connectedDevices: List<Int>
)