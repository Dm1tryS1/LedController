package com.example.smarthome.fragments.charts

import com.example.smarthome.base.presentation.BaseViewModel
import com.github.mikephil.charting.data.Entry

class ChartsViewModel : BaseViewModel<ChartsState, ChartsEvent>() {

    override fun createInitialState(): ChartsState {
        return ChartsState(listOf())
    }

    fun buildChart() {
        val entries = mutableListOf<Entry>()
        entries.add(Entry(1f, 20f))
        entries.add(Entry(2f, 17f))
        entries.add(Entry(3f, 19f))
        entries.add(Entry(4f, 19f))
        entries.add(Entry(5f, 23f))
        entries.add(Entry(6f, 21f))

        updateState {
            ChartsState(entries)
        }
    }
}