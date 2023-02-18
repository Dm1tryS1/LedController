package com.example.smarthome.fragments.charts

import android.annotation.SuppressLint
import com.example.smarthome.base.presentation.BaseViewModel
import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat
import java.util.*

class ChartsViewModel : BaseViewModel<ChartsState, ChartsEvent>() {

    override fun createInitialState(): ChartsState {
        return ChartsState(listOf(), arrayListOf())
    }

    fun buildChart() {
        val data = listOf(
            Data(convertDateToLong("03:04"), 20L),
            Data(convertDateToLong("03:05"), 1L),
            Data(convertDateToLong("03:06"), 19L),
            Data(convertDateToLong("10:07"), 23L),
            Data(convertDateToLong("13:08"), 21L),
            Data(convertDateToLong("13:09"), 21L),
            Data(convertDateToLong("13:10"), 21L),
            Data(convertDateToLong("13:11"), 21L),
            Data(convertDateToLong("13:12"), 21L),
            Data(convertDateToLong("13:13"), 21L),
            Data(convertDateToLong("13:14"), 21L),
            Data(convertDateToLong("19:09"), 19L)
        )

        val entries = data.mapIndexed(::toEntries)

        val dates = arrayListOf<String>()
        (data.map { it.date }).map{
            dates.add(toDates(it))
        }

        updateState {
            ChartsState(entries, dates)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun toDates(date: Long): String {
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(Date(date))
    }
    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("HH:mm")
        return df.parse(date)?.time ?: 0L
    }

    private fun toEntries(ind: Int, data: Data): Entry = Entry(ind.toFloat(), data.value.toFloat())


    data class Data(val date: Long, val value: Long)
}