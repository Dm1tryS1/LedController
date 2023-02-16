package com.example.smarthome.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendConfig(
    @SerialName("connectedDevices")
    val connectedDevices: List<Int>
)