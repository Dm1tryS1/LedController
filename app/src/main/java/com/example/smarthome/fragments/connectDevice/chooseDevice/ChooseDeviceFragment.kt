package com.example.smarthome.fragments.connectDevice.chooseDevice

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.fragmentViewBinding
import com.example.smarthome.databinding.FragmentChooseDeviceBinding
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.Connection
import com.example.smarthome.fragments.connectDevice.chooseDevice.dialog.ConnectionByIP
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.adapter.WifiDeviceAdapter
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.core.utils.supportBottomSheetScroll
import com.example.smarthome.main.ChooseDeviceParams
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseDeviceFragment : BaseFragment<ChooseDeviceState, ChooseDeviceEvent>(R.layout.fragment_choose_device) {

    override val vm: ChooseDeviceViewModel by viewModel {
        parametersOf(getParams(ChooseDeviceParams::class.java)?: ChooseDeviceParams())
    }

    private val binding by fragmentViewBinding(FragmentChooseDeviceBinding::bind)

    private val adapter = WifiDeviceAdapter(onItemClicked = { type, id ->
        vm.onItemClicked(type, id)
    })

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
                vm::connectByIp,
            ).show()
        }
    }
}