package com.example.smarthome.fragments.charts

import com.github.mikephil.charting.data.Entry

data class ChartsState(val data: List<Entry>?, val listDates: ArrayList<String>, val deviceTypes: Int?)
