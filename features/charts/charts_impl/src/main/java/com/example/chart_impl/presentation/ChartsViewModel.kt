package com.example.chart_impl.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.charts_api.ChartsFeature
import com.example.core.presentation.BaseViewModel
import com.github.mikephil.charting.data.Entry
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChartsViewModel(
    private val chartUseCase: com.example.chart_impl.domain.ChartUseCase, router: Router,
    private val chartsParams: ChartsFeature.ChartsParams?,
) : BaseViewModel<ChartsState, ChartsEvent>(router = router) {

    override fun createInitialState(): ChartsState {
        return ChartsState(listOf(), arrayListOf(), chartsParams?.deviceType)
    }

    fun buildChart(
        date: String = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault()
        ).format(Date())
    ) {
        if (chartsParams != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val data = chartUseCase.getDeviceInfo(chartsParams.id, date)
                    .map { deviceInfo ->
                        Data(convertDateToLong(deviceInfo.time), deviceInfo.value.toLong())
                    }

                val entries = data.mapIndexed(::toEntries)

                val dates = arrayListOf<String>()
                (data.map { it.date }).map {
                    dates.add(toDates(it))
                }

                updateState {
                    ChartsState(entries, dates, chartsParams.deviceType)
                }
            }
        }
    }

    fun openDatePicker() {
        sendEvent(ChartsEvent.OpenDatePicker)
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