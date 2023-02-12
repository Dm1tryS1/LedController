package com.example.smarthome.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentSystemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class System : BaseFragment<Unit, Unit>() {

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

            save.setOnClickListener {
                vm.save()
            }
        }
    }

    override fun renderState(state: Unit) = Unit

    override fun handleEvent(event: Unit) = Unit
}