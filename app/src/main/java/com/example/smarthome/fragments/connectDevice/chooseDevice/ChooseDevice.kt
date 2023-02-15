package com.example.smarthome.fragments.connectDevice.chooseDevice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentChooseDeviceBinding
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.Connection
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.ConnectionByIP
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.adapter.WifiDeviceAdapter
import com.example.smarthome.utils.snackBar
import com.example.smarthome.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseDevice : BaseFragment<ChooseDeviceState, ChooseDeviceEvent>() {

    private lateinit var binding: FragmentChooseDeviceBinding

    override val vm: ChooseDeviceViewModel by viewModel { parametersOf(arguments?.getBoolean(BY_IP)) }

    private val adapter = WifiDeviceAdapter(onItemClicked = {
        vm.onItemClicked(it)
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
        private const val BY_IP = "by_ip"
        fun getNewInstance(byIp: Boolean): ChooseDevice {
            return ChooseDevice().apply {
                arguments = Bundle().apply {
                    putBoolean(BY_IP, byIp)
                }
            }
        }
    }
}