package com.example.smarthome.fragments.charts

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentChartsBinding
import com.example.smarthome.utils.SensorDateFormatter
import com.example.smarthome.utils.dp
import com.example.smarthome.utils.setupEnvironments
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel


class Charts : BaseFragment<ChartsState, ChartsEvent>() {

    private lateinit var binding: FragmentChartsBinding
    override val vm: ChartsViewModel by viewModel()

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
        vm.buildChart()
    }

    override fun renderState(state: ChartsState) {
        with(binding) {
            val dataSet =  LineDataSet(state.data, "Температура")
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

            with(chart) {
                setTouchEnabled(false)
                legend.isEnabled = false
                description.isEnabled = false
                setTouchEnabled(false)
                getPaint(Chart.PAINT_INFO).textSize = 14F.dp
                xAxis.apply {
                    setupEnvironments(null, 14F, requireContext())
                    position = XAxis.XAxisPosition.BOTTOM
                    //valueFormatter = SensorDateFormatter()
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

}