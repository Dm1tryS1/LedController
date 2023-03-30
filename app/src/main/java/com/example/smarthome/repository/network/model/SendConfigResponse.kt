package com.example.smarthome.repository.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendConfigResponse(
    @SerialName("connectedDevices")
    val connectedDevices: List<Int>
)