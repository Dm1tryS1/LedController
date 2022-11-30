package com.example.smarthome.fragments.information.recyclerView.mapper

import com.example.smarthome.R
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.common.device.SensorType
import java.nio.ByteBuffer
import kotlin.experimental.and

fun packageToInfoViewItem(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    return when (aPackage.id) {
        1,3 -> packageToTemperatureInfo(aPackage)
        2 -> packageToConditionerInfo(aPackage)
        5,6 -> packageToHumidityInfo(aPackage)
        4 -> packageToHumidifierInfo(aPackage)
        7,8 -> packageToPressure(aPackage)
        else -> {
            InfoViewItem.SensorsInfoViewItem(R.drawable.ic_info, aPackage.id ?: 0, "Неизвестное устройство", "Нет дааных", SensorType.Unknown)
        }
    }
}

fun packageToTemperatureInfo(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info0.let {
        if (it == null)
            "Нет информации"
        else
            "Температура: $it°C"
    }

    return InfoViewItem.SensorsInfoViewItem(R.drawable.ic_temperature, aPackage.id!!, info, date, SensorType.TemperatureSensor)
}

fun packageToHumidityInfo(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info0.let {
        if (it == null)
            "Нет информации"
        else
            "Влажность: $it%"
    }

    return InfoViewItem.SensorsInfoViewItem(R.drawable.ic_humidity, aPackage.id!!, info, date, SensorType.HumidifierSensor)
}

fun packageToHumidifierInfo(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info0.let {
        if (it == null)
            "Нет информации"
        else
            if ((it.toByte() and 128.toByte()) == 0.toByte())
                "Выключено"
            else
                "Включено: ${
                    (it.toByte() and 127.toByte())
                } % воды"
    }

    return InfoViewItem.SensorsInfoViewItem(R.drawable.ic_humidifier, aPackage.id!!, info, date, SensorType.Humidifier)
}

fun packageToConditionerInfo(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info0.let {
        if (it == null)
            "Нет информации"
        else
            if ((it.toByte() and 128.toByte()) == 0.toByte())
                "Выключено"
            else
                "Включено: ${
                    (it.toByte() and 63.toByte()).toString(16)
                } °C"
    }

    return InfoViewItem.SensorsInfoViewItem(R.drawable.ic_conditioner, aPackage.id!!, info, date, SensorType.Conditioner)
}

fun packageToPressure(aPackage: Package): InfoViewItem.SensorsInfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info0.let {
        if (it == null)
            "Нет информации"
        else {
            "Давление: ${ByteBuffer.wrap(byteArrayOf( aPackage.info3!!, aPackage.info2!!, aPackage.info1!!, it)).int} Па"
        }
    }

    return InfoViewItem.SensorsInfoViewItem(R.drawable.ic_pressure, aPackage.id!!, info, date, SensorType.PressureSensor)
}

fun Int.toTime(): String {
    return this.toString().padStart(2,'0')
}
