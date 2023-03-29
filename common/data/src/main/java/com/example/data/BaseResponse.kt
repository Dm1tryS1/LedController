package com.example.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse<T>(
    @SerialName("error")
    val error: ErrorResponse? = null,

    @SerialName("data")
    val data: T? = null,
)

@Serializable
class ErrorResponse(
    @SerialName("statusCode")
    val code: Int,

    @SerialName("message")
    val message: String? = null,
)

