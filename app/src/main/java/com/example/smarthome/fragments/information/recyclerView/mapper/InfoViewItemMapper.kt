package com.example.smarthome.fragments.information.recyclerView.mapper

import com.example.smarthome.R
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema

fun packageToInfoViewItem(deviceInfoSchema: DeviceInfoSchema): InfoViewItem.SensorsInfoViewItem {
    return when (deviceInfoSchema) {
        is DeviceInfoSchema.TemperatureSensorSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.ConditionerSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.HumiditySensorSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.HumidifierSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.PressureSensorSchema -> schemaToInfo(deviceInfoSchema)
    }
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.TemperatureSensorSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours.toTime()}:${
                minutes.toTime()
            }:${seconds.toTime()}"
    }

    val info = deviceInfoSchema.data.let {
        if (it == null)
            "Нет информации"
        else
            "Температура: $it°C"
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_temperature,
        deviceInfoSchema.id!!,
        info,
        date,
        SensorType.TemperatureSensor
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.HumiditySensorSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours.toTime()}:${
                minutes.toTime()
            }:${seconds.toTime()}"
    }

    val info = deviceInfoSchema.data.let {
        if (it == null)
            "Нет информации"
        else
            "Влажность: $it%"
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_humidity,
        deviceInfoSchema.id!!,
        info,
        date,
        SensorType.HumiditySensor
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.HumidifierSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours.toTime()}:${
                minutes.toTime()
            }:${seconds.toTime()}"
    }

    val info = deviceInfoSchema.status.let {
        if (!it)
            "Выключено"
        else
            "Включено: ${
                (deviceInfoSchema.water)
            } % воды"
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_humidifier,
        deviceInfoSchema.id!!,
        info,
        date,
        SensorType.Humidifier
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.ConditionerSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours.toTime()}:${
                minutes.toTime()
            }:${seconds.toTime()}"
    }

    val info = deviceInfoSchema.status.let {
        if (!it)
            "Выключено"
        else if (deviceInfoSchema.temperature != null) {
            "Включено: ${
                deviceInfoSchema.temperature
            } °C"
        }else {
            "Включено"
        }
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_conditioner,
        deviceInfoSchema.id!!,
        info,
        date,
        SensorType.Conditioner
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.PressureSensorSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours.toTime()}:${
                minutes.toTime()
            }:${seconds.toTime()}"
    }

    val info = deviceInfoSchema.data.let {
        if (it == null)
            "Нет информации"
        else {
            "Давление: ${it} Па"
        }
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_pressure,
        deviceInfoSchema.id!!,
        info,
        date,
        SensorType.PressureSensor
    )
}

fun Int.toTime(): String {
    return this.toString().padStart(2, '0')
}
