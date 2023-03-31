package com.example.chart_impl.presentation.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class SensorDateFormatter : ValueFormatter() {

    private var dates = ArrayList<String>()

    override fun getAxisLabel(value: Float, axisBase: AxisBase): String {
        return when {
            dates.isEmpty() -> "-"
            value == axisBase.mEntries[axisBase.mEntryCount - 1] -> dates.last()
            value.toInt() in 0 until dates.size -> dates[value.toInt()]
            else -> "-"
        }
    }

    fun setDates(dates: ArrayList<String>) {
        this.dates = dates
    }
}