package com.example.smarthome.fragments.information.recyclerView.mapper

import com.example.smarthome.R
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.information.data.DeviceInfoSchema

fun packageToInfoViewItem(deviceInfoSchema: DeviceInfoSchema): InfoViewItem.SensorsInfoViewItem {
    return when (deviceInfoSchema) {
        is DeviceInfoSchema.ConditionerSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.HumidifierSchema -> schemaToInfo(deviceInfoSchema)
        is DeviceInfoSchema.Sensors -> schemaToInfo(deviceInfoSchema)
    }
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.Sensors): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        "${hours.toTime()}:${
            minutes.toTime()
        }:${seconds.toTime()}"
    }

    val info = when (deviceInfoSchema.type) {
        SensorType.HumiditySensor.type -> "Влажность: ${deviceInfoSchema.data}%"
        SensorType.PressureSensor.type -> "Давление: ${deviceInfoSchema.data} Па"
        SensorType.TemperatureSensor.type -> "Температура: ${deviceInfoSchema.data}°C"
        else -> ""
    }

    val icon = when (deviceInfoSchema.type) {
        SensorType.HumiditySensor.type -> R.drawable.ic_humidity
        SensorType.PressureSensor.type -> R.drawable.ic_pressure
        SensorType.TemperatureSensor.type -> R.drawable.ic_temperature
        else -> R.drawable.ic_info
    }

    val type = when (deviceInfoSchema.type) {
        SensorType.TemperatureSensor.type -> SensorType.TemperatureSensor
        SensorType.HumiditySensor.type -> SensorType.HumiditySensor
        SensorType.PressureSensor.type -> SensorType.PressureSensor
        else -> SensorType.Unknow
    }

    return InfoViewItem.SensorsInfoViewItem(
        icon,
        deviceInfoSchema.id,
        info,
        date,
        type,
        true
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.HumidifierSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        "${hours.toTime()}:${
            minutes.toTime()
        }:${seconds.toTime()}"
    }

    val info = if (deviceInfoSchema.status != null) {
        if (deviceInfoSchema.status)
            "Выключено"
        else
            "Включено: ${
                (deviceInfoSchema.water)
            } % воды"
    } else {
        "Увлажнитель воздуха"
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_humidifier,
        deviceInfoSchema.id,
        info,
        date,
        SensorType.Humidifier,
        deviceInfoSchema.status ?: false
    )
}

fun schemaToInfo(deviceInfoSchema: DeviceInfoSchema.ConditionerSchema): InfoViewItem.SensorsInfoViewItem {
    val date = with(deviceInfoSchema) {
        "${hours.toTime()}:${
            minutes.toTime()
        }:${seconds.toTime()}"
    }

    val info = if (deviceInfoSchema.status != null) {
        if (!deviceInfoSchema.status) {
            "Выключено"
        } else {
            "Включено: ${
                deviceInfoSchema.temperature
            } °C"
        }
    } else {
        "Кондиционер"
    }

    return InfoViewItem.SensorsInfoViewItem(
        R.drawable.ic_conditioner,
        deviceInfoSchema.id,
        info,
        date,
        SensorType.Conditioner,
        deviceInfoSchema.status ?: false
    )
}

fun Int.toTime(): String {
    return this.toString().padStart(2, '0')
}
