package com.example.smarthome.fragments.information.data

import kotlinx.serialization.Serializable

@Serializable
data class Package(
    var id: Int?,
    var type: Int?,
    var hours: Int?,
    var minutes: Int?,
    var seconds: Int?,
    var info0: Byte?,
    var info1: Byte?,
    var info2: Byte?,
    var info3: Byte?,
)

sealed class DeviceInfoSchema {

    abstract val id: Int?
    abstract val type: Int?
    abstract val hours: Int?
    abstract val minutes: Int?
    abstract val seconds: Int?
    data class TemperatureSensorSchema(
        override val id: Int?,
        override val type: Int?,
        override val hours: Int?,
        override val minutes: Int?,
        override val seconds: Int?,
        val data: Int?,
        val notification: Boolean,
        val more: Boolean?,
        val comfortableValue: Int?
    ) : DeviceInfoSchema()

    data class PressureSensorSchema(
        override val id: Int?,
        override val type: Int?,
        override val hours: Int?,
        override val minutes: Int?,
        override val seconds: Int?,
        val data: Int?,
    ) : DeviceInfoSchema()

    data class HumiditySensorSchema(
        override val id: Int?,
        override val type: Int?,
        override val hours: Int?,
        override val minutes: Int?,
        override val seconds: Int?,
        val data: Int?,
        val notification: Boolean,
        val more: Boolean?,
        val comfortableValue: Int?
    ) : DeviceInfoSchema()

    data class HumidifierSchema(
        override val id: Int?,
        override val type: Int?,
        override val hours: Int?,
        override val minutes: Int?,
        override val seconds: Int?,
        val status: Boolean,
        val water: Int?,
    ) : DeviceInfoSchema()

    data class ConditionerSchema(
        override val id: Int?,
        override val type: Int?,
        override val hours: Int?,
        override val minutes: Int?,
        override val seconds: Int?,
        val status: Boolean,
        val temperature: Int?,
    ) : DeviceInfoSchema()

}