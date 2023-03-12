package com.example.smarthome.fragments.connectDevice.remoteControl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.common.device.ConditionerCommands
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.databinding.FragmentRemoteControlBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemoteControlFragment : BaseFragment<RemoteControlState, RemoteControlEvent>() {

    private lateinit var binding: FragmentRemoteControlBinding

    override val vm: RemoteControlViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemoteControlBinding.inflate(inflater)
        return binding.root
    }

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
                vm.writeCommandForRemoteControl(SensorType.Conditioner.type, ConditionerCommands.On.command)
            }
            condOff.setOnClickListener {
                vm.writeCommandForRemoteControl(SensorType.Conditioner.type, ConditionerCommands.Off.command)
            }
            condAddTemp.setOnClickListener {
                vm.writeCommandForRemoteControl(SensorType.Conditioner.type, ConditionerCommands.AddTemperature.command)
            }
            condReduceTemp.setOnClickListener {
                vm.writeCommandForRemoteControl(SensorType.Conditioner.type, ConditionerCommands.ReduceTemperature.command)
            }
        }
    }


    override fun renderState(state: RemoteControlState) {
        when (state) {
            is RemoteControlState.ShowCommands -> {
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
            }
        }
    }

    override fun handleEvent(event: RemoteControlEvent) {
        when (event) {
            is RemoteControlEvent.OnSuccess -> snackBar(getString(R.string.remote_control_success))
            is RemoteControlEvent.OnError -> snackBar(getString(R.string.remote_control_error))
            else -> {}
        }
    }


}