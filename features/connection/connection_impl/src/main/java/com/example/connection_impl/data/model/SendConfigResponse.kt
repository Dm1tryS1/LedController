package com.example.connection_impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendConfigResponse(
    @SerialName("connectedDevices")
    val connectedDevices: List<Int>
)