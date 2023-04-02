package com.example.connection_impl.presentation.remote_control

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.connection_impl.R
import com.example.connection_impl.databinding.FragmentRemoteControlBinding
import com.example.data.device.ConditionerCommands
import com.example.data.device.HumidifierCommands
import com.example.data.device.SensorType
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.core.snackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemoteControlFragment : BaseFragment<RemoteControlState, RemoteControlEvent>(R.layout.fragment_remote_control) {

    private val binding by fragmentViewBinding(FragmentRemoteControlBinding::bind)

    override val vm: RemoteControlViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            radioGroup.check(cond.id)
            radioGroup.setOnCheckedChangeListener { _, i ->
                when (i) {
                    cond.id -> vm.changeList(RemoteControlViewModel.Type.TypeCond)
                    else -> vm.changeList(RemoteControlViewModel.Type.TypeHum)
                }
            }
            condOn.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Conditioner.type,
                    ConditionerCommands.On.command
                )
            }
            condOff.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Conditioner.type,
                    ConditionerCommands.Off.command
                )
            }
            condAddTemp.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Conditioner.type,
                    ConditionerCommands.AddTemperature.command
                )
            }
            condReduceTemp.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Conditioner.type,
                    ConditionerCommands.ReduceTemperature.command
                )
            }
            humOn.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Humidifier.type,
                    HumidifierCommands.On.command
                )
            }
            humOff.setOnClickListener {
                vm.writeCommandForRemoteControl(
                    SensorType.Humidifier.type,
                    HumidifierCommands.Off.command
                )
            }
        }
    }


    override fun renderState(state: RemoteControlState) {
        when (state.deviceType) {
            RemoteControlViewModel.Type.TypeCond -> {
                binding.condCommands.isVisible = true
                binding.humCommands.isVisible = false
            }
            RemoteControlViewModel.Type.TypeHum -> {
                binding.condCommands.isVisible = false
                binding.humCommands.isVisible = true
            }
        }
        binding.loader.isVisible = state.isLoading
    }

    override fun handleEvent(event: RemoteControlEvent) {
        when (event) {
            is RemoteControlEvent.ShowToast -> snackBar(getString(event.message))
        }
    }


}