package com.example.smarthome.fragments.information

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.smarthome.databinding.FragmentInformationBinding
import com.example.smarthome.fragments.information.dialog.*
import com.example.smarthome.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.utils.Command
import com.example.smarthome.utils.SensorType
import com.example.smarthome.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val vm: InformationViewModel by viewModel()
    private val adapter =
        InformationAdapter(onMenuClicked = { type, id, info, date ->
            vm.onMenuClicked(
                type,
                id,
                info,
                date
            )
        })

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

                var currentType = SensorType.Unknown
                val items = mutableListOf<InfoViewItem>()
                state.data.forEach {
                    if (currentType.type != it.sensorType.type) {
                        currentType = it.sensorType
                        items.add(InfoViewItem.Header(it.sensorType.text))
                    }
                    items.add(it)
                }
                adapter.items = items

                if (state.progressVisibility)
                    requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                else
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                binding.progressBar.isVisible = state.progressVisibility
            } else
                sensors.isVisible = false

        }

        vm.event.observe(activity as LifecycleOwner) { event ->
            when (event) {
                is InformationEvent.OpenSensorMenuEvent -> {
                    Sensor.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
                        resources = event.resources,
                        command = event.command,
                        data = event.data,
                        date = event.date
                    ).show()
                }
                is InformationEvent.OpenConditionerMenuEvent -> {
                    Conditioner.create(
                        fragment = this@Information,
                        action = vm::sendPackage,
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