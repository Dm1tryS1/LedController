package com.example.smarthome.fragments.information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.smarthome.databinding.FragmentInformationBinding
import com.example.smarthome.fragments.information.dialog.*
import com.example.smarthome.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.smarthome.utils.Command
import com.example.smarthome.utils.supportBottomSheetScroll
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
        Log.d("here", "here1")
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            vm.initializeState()
    }
}