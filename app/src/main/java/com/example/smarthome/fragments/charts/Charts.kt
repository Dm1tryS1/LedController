package com.example.smarthome.fragments.charts

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.databinding.FragmentChartsBinding
import com.example.smarthome.fragments.charts.formatter.SensorDateFormatter
import com.example.smarthome.fragments.charts.formatter.SensorValueFormatter
import com.example.smarthome.utils.dp
import com.example.smarthome.utils.setupEnvironments
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel

class Charts : BaseFragment<ChartsState, ChartsEvent>() {

    private var xAxisFormatter = SensorDateFormatter()

    private lateinit var binding: FragmentChartsBinding
    override val vm: ChartsViewModel by viewModel()

    private var deviceTypes: Int? = arguments?.getInt(DEVICE_TYPE)
    private var id: Int? = arguments?.getInt(ID)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceTypes = arguments?.getInt(DEVICE_TYPE)
        id = arguments?.getInt(ID)
        vm.buildChart()
    }

    override fun renderState(state: ChartsState) {
        val valueFormatterValue: ValueFormatter
        with(binding) {
            xAxisFormatter.setDates(state.listDates)

            val dataSet = LineDataSet(state.data, "Graph")
            with(dataSet) {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                cubicIntensity = 0.2f
                setDrawFilled(true)
                setDrawCircleHole(false)
                lineWidth = 1.0f
                setCircleColor(getColor(requireContext(), R.color.white))
                highLightColor = Color.rgb(244, 117, 117)
                color = getColor(requireContext(), R.color.blue)
                fillAlpha =20
                setDrawHorizontalHighlightIndicator(false)
                fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
            }

            when (deviceTypes) {
                SensorType.TemperatureSensor.type -> {
                    valueFormatterValue = SensorValueFormatter("°C")
                    title.text = getString(R.string.chart_graph_temp)
                }

                SensorType.HumidifierSensor.type -> {
                    valueFormatterValue = SensorValueFormatter("%")
                    title.text = getString(R.string.chart_graph_hum)
                }

                SensorType.PressureSensor.type -> {
                    valueFormatterValue  = SensorValueFormatter("Па")
                    title.text = getString(R.string.chart_graph_pressure)
                }
                else -> {
                    valueFormatterValue  = SensorValueFormatter("")
                }
            }


            with(chart) {
                axisLeft.valueFormatter = valueFormatterValue
                setTouchEnabled(false)
                legend.isEnabled = false
                description.isEnabled = false
                setTouchEnabled(false)
                getPaint(Chart.PAINT_INFO).textSize = 14F.dp
                xAxis.apply {
                    setupEnvironments(null, 14F, requireContext())
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = xAxisFormatter
                }
                axisLeft.setupEnvironments(null, 14F, requireContext())
                axisRight.isEnabled = false
                extraBottomOffset = 10F
                invalidate()

                val chartData = LineData(dataSet)
                chartData.setValueTextSize(9f)
                chartData.setDrawValues(false)

                data = chartData

                animateX(500)
            }
        }
    }

    override fun handleEvent(event: ChartsEvent) { }

    companion object {
        private const val DEVICE_TYPE = "device_type"
        private const val ID = "id"
        fun getNewInstance(deviceType: Int, id: Int): Charts {
            return Charts().apply {
                arguments = Bundle().apply {
                    putInt(DEVICE_TYPE, deviceType)
                    putInt(ID, id)
                }
            }
        }
    }

}