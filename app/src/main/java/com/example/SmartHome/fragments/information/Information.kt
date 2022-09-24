package com.example.SmartHome.fragments.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.SmartHome.databinding.FragmentInformationBinding
import com.example.SmartHome.fragments.information.dialog.*
import com.example.SmartHome.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.SmartHome.utils.Command
import com.example.SmartHome.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val vm: InformationViewModel by viewModel()
    private val adapter =
        InformationAdapter(onMenuClicked = { id -> vm.onMenuClicked(id) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        sensors.adapter = adapter
        sensors.supportBottomSheetScroll()

        vm.state.observe(activity as LifecycleOwner) { state ->
            if (state.data != null) {
                sensors.isVisible = true
                adapter.items = state.data
            } else {
                sensors.isVisible = false
            }
        }

        vm.event.observe(activity as LifecycleOwner) { event ->
            when (event) {
                is InformationEvent.OpenTemperatureMenuEvent -> {
                    TemperatureSensor.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
                        data = event.temperature,
                        date = event.date
                    ).show()
                }
                is InformationEvent.OpenConditionerMenuEvent -> {
                    Conditioner.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
                    ).show()
                }
                is InformationEvent.OpenHumidityMenuEvent -> {
                    HumiditySensor.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
                        data = event.humidity,
                        date = event.date
                    ).show()
                }
                is InformationEvent.OpenHumidifierMenuEvent -> {
                    Humidifier.create(
                        fragment = this@Information,
                        action = vm::sendPackage
                    ).show()
                }
                is InformationEvent.OpenSettingsMenuEvent -> {
                    Settings.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
                        progress = event.value,
                        save = vm::saveUserSettings
                    ).show()
                }
            }
        }

        reloadButton.setOnClickListener {
            vm.sendPackage(Command.BroadCast.command)
        }

        settings.setOnClickListener {
            vm.onSettingsClicked()
        }

        vm.getInfo()
        vm.initializeState()

    }
}