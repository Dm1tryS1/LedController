package com.example.connection_impl.presentation.choose_device

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.connection_impl.R
import com.example.connection_impl.databinding.FragmentChooseDeviceBinding
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.connection_impl.presentation.choose_device.dialog.Connection
import com.example.connection_impl.presentation.choose_device.dialog.ConnectionByIP
import com.example.connection_impl.presentation.choose_device.recyclerView.adapter.WifiDeviceAdapter
import com.example.connection_impl.presentation.data.ChooseDeviceParams
import com.example.core.snackBar
import com.example.core.utils.supportBottomSheetScroll
import com.example.data.device.ControlType
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseDeviceFragment :
    BaseFragment<ChooseDeviceState, ChooseDeviceEvent>(R.layout.fragment_choose_device) {

    override val vm: ChooseDeviceViewModel by viewModel {
        parametersOf(
            getParams(ChooseDeviceParams::class.java)
                ?: ChooseDeviceParams(ControlType.Connect)
        )
    }

    private val binding by fragmentViewBinding(FragmentChooseDeviceBinding::bind)

    private val adapter = WifiDeviceAdapter(onItemClicked = { id ->
        vm.onItemClicked(id)
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