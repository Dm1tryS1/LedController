package com.example.smarthome.fragments.connectDevice

import android.os.Bundle
import android.view.View
import com.example.smarthome.R
import com.example.data.device.ControlType
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.smarthome.databinding.FragmentConnectDeviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectDeviceFragment : BaseFragment<Unit, Unit>(R.layout.fragment_connect_device) {

    private val binding by fragmentViewBinding(FragmentConnectDeviceBinding::bind)

    override val vm: ConnectDeviceViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            next.setOnClickListener {
                vm.onNextClicked(ControlType.Connect)
            }
            byIp.setOnClickListener {
                vm.onNextClicked(ControlType.IP)
            }
            remoteControl.setOnClickListener {
                vm.onRemoteControlClicked()
            }
        }
    }


    override fun renderState(state: Unit) = Unit

    override fun handleEvent(event: Unit) = Unit


}