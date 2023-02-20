package com.example.smarthome.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentSystemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class System : BaseFragment<SystemState, Unit>() {

    private lateinit var binding: FragmentSystemBinding
    override val vm: SystemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSystemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            minTemp.text.setText(R.string.system_min_temp)
            maxTemp.text.setText(R.string.system_max_temp)
            minHum.text.setText(R.string.system_min_humidity)
            maxHum.text.setText(R.string.system_max_humidity)

            minTemp.numberPicker.apply {
                minValue = MIN_TEMP_VALUE
                maxValue = MAX_TEMP_VALUE
                setOnValueChangedListener { _, _, value ->
                    maxTemp.numberPicker.minValue = value
                }
            }

            maxTemp.numberPicker.apply {
                minValue = MIN_TEMP_VALUE
                maxValue = MAX_TEMP_VALUE
                setOnValueChangedListener { _, _, value ->
                    minTemp.numberPicker.maxValue = value
                }
            }

            minHum.numberPicker.apply {
                minValue = MIN_HUM_VALUE
                maxValue = MAX_HUM_VALUE
                setOnValueChangedListener { _, _, value ->
                    maxHum.numberPicker.minValue = value
                }
            }

            maxHum.numberPicker.apply {
                minValue = MIN_HUM_VALUE
                maxValue = MAX_HUM_VALUE
                setOnValueChangedListener { _, _, value ->
                    minHum.numberPicker.maxValue = value
                }
            }

            save.setOnClickListener {
                vm.save(
                    maxTemp.numberPicker.value,
                    minTemp.numberPicker.value,
                    maxHum.numberPicker.value,
                    minHum.numberPicker.value,
                    when(radioGroup.checkedRadioButtonId) {
                        R.id.btn_max_temp -> 0
                        R.id.btn_min_temp -> 1
                        R.id.btn_max_hum -> 2
                        R.id.btn_min_hum -> 3
                        else -> 4
                    }
                )
            }
            cancel.setOnClickListener {
                vm.clearSettings()
            }
        }
    }

    override fun renderState(state: SystemState) {
        with(binding) {
            when (state) {
                is SystemState.Settings -> {
                    maxTemp.numberPicker.value = state.maxTemp
                    minTemp.numberPicker.value = state.minTemp
                    minHum.numberPicker.value = state.minHum
                    maxHum.numberPicker.value = state.maxHum
                    radioGroup.check(
                        when (state.displayedValue) {
                            0 -> R.id.btn_max_temp
                            1 -> R.id.btn_min_temp
                            2 -> R.id.btn_max_hum
                            3 -> R.id.btn_min_hum
                            else -> R.id.btn_timer
                        }
                    )
                }
                is SystemState.Loading -> {
                    loader.isVisible = true
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        }
    }

    override fun handleEvent(event: Unit) {}

    companion object {
        const val MIN_TEMP_VALUE = 18
        const val MAX_TEMP_VALUE = 28
        const val MIN_HUM_VALUE = 30
        const val MAX_HUM_VALUE = 60
        const val DISPLAYED_VALUE = 4
    }
}