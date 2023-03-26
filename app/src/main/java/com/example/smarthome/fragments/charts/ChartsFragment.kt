package com.example.smarthome.fragments.charts

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import com.example.smarthome.R
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.dp
import com.example.smarthome.core.utils.fragmentViewBinding
import com.example.smarthome.core.utils.setupEnvironments
import com.example.smarthome.databinding.FragmentChartsBinding
import com.example.smarthome.fragments.charts.formatter.SensorDateFormatter
import com.example.smarthome.fragments.charts.formatter.SensorValueFormatter
import com.example.smarthome.fragments.information.recyclerView.mapper.toTime
import com.example.smarthome.main.ChartsParams
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class ChartsFragment : BaseFragment<ChartsState, ChartsEvent>(R.layout.fragment_charts) {

    private var xAxisFormatter = SensorDateFormatter()

    private val binding by fragmentViewBinding(FragmentChartsBinding::bind)

    override val vm: ChartsViewModel by viewModel {
        parametersOf(getParams(ChartsParams::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.buildChart()
        binding.setDate.setOnClickListener {
            vm.openDatePicker()
        }
    }

    override fun renderState(state: ChartsState) {
        val valueFormatterValue: ValueFormatter
        with(binding) {
            xAxisFormatter.setDates(state.listDates)

            val dataSet = LineDataSet(state.data, "Graph")
            with(dataSet) {
                mode = LineDataSet.Mode.LINEAR
                setDrawFilled(true)
                setDrawCircleHole(false)
                lineWidth = 1.0f
                setCircleColor(getColor(requireContext(), R.color.white))
                highLightColor = Color.rgb(244, 117, 117)
                color = getColor(requireContext(), R.color.blue)
                fillAlpha = 20
                setDrawHorizontalHighlightIndicator(false)
                fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
            }

            when (state.deviceTypes) {
                SensorType.TemperatureSensor -> {
                    valueFormatterValue = SensorValueFormatter("°C")
                    title.text = getString(R.string.chart_graph_temp)
                }

                SensorType.HumiditySensor -> {
                    valueFormatterValue = SensorValueFormatter("%")
                    title.text = getString(R.string.chart_graph_hum)
                }

                SensorType.PressureSensor -> {
                    valueFormatterValue = SensorValueFormatter("Па")
                    title.text = getString(R.string.chart_graph_pressure)
                }
                else -> {
                    valueFormatterValue = SensorValueFormatter("")
                }
            }


            with(chart) {
                axisLeft.valueFormatter = valueFormatterValue
                setTouchEnabled(false)
                legend.isEnabled = false
                description.isEnabled = false
                setTouchEnabled(false)
                getPaint(Chart.PAINT_INFO).textSize = 8F.dp
                xAxis.apply {
                    setupEnvironments(null, 8F, requireContext())
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = xAxisFormatter
                }
                axisLeft.setupEnvironments(null, 8F, requireContext())
                axisRight.isEnabled = false
                extraBottomOffset = 16F
                invalidate()

                val chartData = LineData(dataSet)
                chartData.setValueTextSize(8f)
                chartData.setDrawValues(false)

                data = chartData

                animateX(500)
            }
        }
    }

    override fun handleEvent(event: ChartsEvent) {
        when (event) {
            is ChartsEvent.OpenDatePicker -> {
                val date = Calendar.getInstance()
                DatePickerDialog(
                    requireContext(),
                    AlertDialog.THEME_HOLO_DARK,
                    { _, year, month, day ->
                        vm.buildChart("${day.toTime()}-${month.inc().toTime()}-$year")
                    },
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }
        }
    }

}