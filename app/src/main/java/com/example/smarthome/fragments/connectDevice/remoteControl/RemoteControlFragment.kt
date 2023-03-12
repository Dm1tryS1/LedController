package com.example.smarthome.fragments.connectDevice.remoteControl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.smarthome.core.base.presentation.BaseFragment
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
    }


}