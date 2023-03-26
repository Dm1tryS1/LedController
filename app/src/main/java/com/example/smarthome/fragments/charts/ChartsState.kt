package com.example.smarthome.fragments.charts

import com.example.smarthome.common.device.SensorType
import com.github.mikephil.charting.data.Entry

data class ChartsState(val data: List<Entry>?, val listDates: ArrayList<String>, val deviceTypes: SensorType?)
