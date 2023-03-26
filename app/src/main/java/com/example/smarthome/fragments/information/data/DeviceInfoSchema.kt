package com.example.smarthome.fragments.information.data
sealed class DeviceInfoSchema {

    abstract val id: Int
    abstract val type: Int
    abstract val hours: Int
    abstract val minutes: Int
    abstract val seconds: Int

    sealed class Sensors : DeviceInfoSchema() {
        abstract val data: Int
        data class PressureSensorSchema(
            override val id: Int,
            override val type: Int,
            override val hours: Int,
            override val minutes: Int,
            override val seconds: Int,
            override val data: Int,
        ) : Sensors()

        data class HumidityAndTemperatureSensorSchema(
            override val id: Int,
            override val type: Int,
            override val hours: Int,
            override val minutes: Int,
            override val seconds: Int,
            override val data: Int,
            val notification: Boolean,
            val min: Int,
            val max: Int
        ) : Sensors()
    }
    data class HumidifierSchema(
        override val id: Int,
        override val type: Int,
        override val hours: Int,
        override val minutes: Int,
        override val seconds: Int,
        val status: Boolean?,
        val water: Int?,
    ) : DeviceInfoSchema()

    data class ConditionerSchema(
        override val id: Int,
        override val type: Int,
        override val hours: Int,
        override val minutes: Int,
        override val seconds: Int,
        val status: Boolean?,
        val temperature: Int?,
    ) : DeviceInfoSchema()

}