package com.example.smarthome.fragments.connectDevice.chooseDevice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.common.device.ControlType
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentChooseDeviceBinding
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.Connection
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.ConnectionByIP
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.adapter.WifiDeviceAdapter
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.core.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseDeviceFragment : BaseFragment<ChooseDeviceState, ChooseDeviceEvent>() {

    private lateinit var binding: FragmentChooseDeviceBinding

    override val vm: ChooseDeviceViewModel by viewModel {
        when (arguments?.getInt(CONTROL_TYPE,0)) {
            0 -> parametersOf(ControlType.Connect)
            1 -> parametersOf(ControlType.IP)
            else -> parametersOf(ControlType.Connect)
        }
    }

    private val adapter = WifiDeviceAdapter(onItemClicked = { type, id ->
        vm.onItemClicked(type, id)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseDeviceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            devices.adapter = adapter
            devices.supportBottomSheetScroll()
            radioGroup.check(cond.id)
            radioGroup.setOnCheckedChangeListener { _, i ->
                when (i) {
                    cond.id -> vm.changeList(ChooseDeviceViewModel.Type.TypeCond)
                    else -> vm.changeList(ChooseDeviceViewModel.Type.TypeHum)
                }
            }
        }
    }

    override fun renderState(state: ChooseDeviceState) {
        when (state) {
            is ChooseDeviceState.OnSuccess -> adapter.items = state.devices
            is ChooseDeviceState.Loading -> {
                binding.loader.isVisible = state.isLoading
            }
        }
    }

    override fun handleEvent(event: ChooseDeviceEvent) {
        when (event) {
            is ChooseDeviceEvent.OpenDeviceMenu -> Connection.create(
                event.id,
                this,
                event.wifiInfo,
                vm::connect,
            ).show()
            is ChooseDeviceEvent.OnError -> snackBar(getString(event.message))
            is ChooseDeviceEvent.OnSuccess -> snackBar(getString(R.string.connect_device_success))
            is ChooseDeviceEvent.OpenDeviceMenuByIP -> ConnectionByIP.create(
                event.id,
                this,
                vm::connectByIp
            ).show()
        }
    }

    companion object {
        private const val CONTROL_TYPE = "CONTROL_TYPE"
        fun getNewInstance(controlType: Int): ChooseDeviceFragment {
            return ChooseDeviceFragment().apply {
                arguments = Bundle().apply {
                    putInt(CONTROL_TYPE, controlType)
                }
            }
        }
    }
}