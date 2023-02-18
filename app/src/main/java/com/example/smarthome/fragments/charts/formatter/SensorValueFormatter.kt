package com.example.smarthome.fragments.charts.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class SensorValueFormatter(private val suffix: String) : ValueFormatter() {

    private val formatter = DecimalFormat("###,###,###,##0.0")

    override fun getFormattedValue(value: Float) = formatter.format(value) + suffix

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return when {
            axis is XAxis -> formatter.format(value)
            else -> formatter.format(value) + suffix
        }
    }
}