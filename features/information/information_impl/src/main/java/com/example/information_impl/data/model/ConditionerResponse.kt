package com.example.information_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
class ConditionerResponse(
    val id: Int,
    val deviceType: Int,
    val status: Boolean? = false,
    val temperature: Int?
)