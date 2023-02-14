package com.example.smarthome.fragments.connectDevice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentConnectDeviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectDevice : BaseFragment<Unit, ConnectDeviceEvent>() {

    private lateinit var binding: FragmentConnectDeviceBinding

    override val vm: ConnectDeviceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectDeviceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            next.setOnClickListener {
                vm.onNextClicked()
            }
        }
    }


    override fun renderState(state: Unit) {}

    override fun handleEvent(event: ConnectDeviceEvent) {
    }


}