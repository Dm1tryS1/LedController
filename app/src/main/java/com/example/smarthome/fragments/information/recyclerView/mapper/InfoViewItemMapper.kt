package com.example.smarthome.fragments.information.recyclerView.mapper

import com.example.smarthome.R
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.data.device.SensorType
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

    var info = ""
    var icon = R.drawable.ic_info

    when (deviceInfoSchema.type) {
        SensorType.HumiditySensor -> {
            info = "Влажность: ${deviceInfoSchema.data}%"
            icon = R.drawable.ic_humidity
        }
        SensorType.PressureSensor -> {
            info = "Давление: ${deviceInfoSchema.data} Па"
            icon = R.drawable.ic_pressure

        }
        SensorType.TemperatureSensor -> {
            info = "Температура: ${deviceInfoSchema.data}°C"
            icon = R.drawable.ic_temperature
        }
        else -> { }
    }

    return InfoViewItem.SensorsInfoViewItem(
        icon,
        deviceInfoSchema.id,
        info,
        date,
        deviceInfoSchema.type,
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
        deviceInfoSchema.type,
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
        deviceInfoSchema.type,
        deviceInfoSchema.status ?: false
    )
}

fun Int.toTime(): String {
    return this.toString().padStart(2, '0')
}
