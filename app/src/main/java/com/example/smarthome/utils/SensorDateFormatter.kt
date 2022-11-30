package com.example.smarthome.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class SensorDateFormatter : ValueFormatter() {

    private var dates = ArrayList<String>()

    override fun getAxisLabel(value: Float, axisBase: AxisBase): String {
        return if (dates.isEmpty()) "-"
        else if  (value == axisBase.mEntries[axisBase.mEntryCount - 1]) {
            dates.last()
        } else if (value.toInt() < dates.size)
            dates[value.toInt()]
        else
            "-"
    }

    fun setDates(dates: ArrayList<String>) {
        this.dates = dates
    }
}