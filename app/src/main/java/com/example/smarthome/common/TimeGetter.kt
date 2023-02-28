package com.example.smarthome.common

import java.util.*

data class Time(val hours: Int, val minutes: Int, val seconds: Int)

fun getTime(): Time {
    val time = Calendar.getInstance(Locale("ru", "RU"))
    return Time(
        time.get(Calendar.HOUR_OF_DAY),
        time.get(Calendar.MINUTE),
        time.get(Calendar.SECOND)
    )
}

