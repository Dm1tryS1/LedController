package com.example.smarthome.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.databinding.FragmentSettingsBinding
import com.example.smarthome.fragments.settings.dialog.Connection
import com.example.smarthome.fragments.settings.recyclerView.adapter.DeviceAdapter
import com.example.smarthome.core.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>() {

    private lateinit var binding: FragmentSettingsBinding

    override val vm: SettingsViewModel by viewModel()

    private val adapter =
        DeviceAdapter(onDeviceClicked = { address ->
            vm.onItemClicked(address)
        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        vm.findDevices()
        recyclerView.adapter = adapter
        recyclerView.supportBottomSheetScroll()

        reload.setOnClickListener {
            vm.findDevices()
        }

        wifi.setOnClickListener {
            vm.onWifiClicked()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            vm.findDevices()
    }

    override fun renderState(state: SettingsState) {
        adapter.items = state.devices

        binding.loader.isVisible = state.isLoading
        if (state.isLoading) {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }


    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ConnectionSuccessEvent -> Toast.makeText(
                requireContext(),
                "Соединено",
                Toast.LENGTH_SHORT
            ).show()
            is SettingsEvent.ConnectionFailureEvent -> Toast.makeText(
                requireContext(),
                "Ошибка",
                Toast.LENGTH_SHORT
            ).show()
            is SettingsEvent.OnItemClickedEvent -> Connection.create(
                fragment = this@SettingsFragment,
                connectAction = vm::connect,
                disconnectAction = vm::disconnect,
                address = event.address,
                wifiInfo = event.wifiInfo
            ).show()
            is SettingsEvent.DisconnectFailureEvent -> Toast.makeText(
                requireContext(),
                "Ошибка",
                Toast.LENGTH_SHORT
            ).show()
            is SettingsEvent.DisconnectSuccessEvent -> Toast.makeText(
                requireContext(),
                "Отключено",
                Toast.LENGTH_SHORT
            ).show()
            is SettingsEvent.Error -> snackBar(getString(event.message))
        }
    }

}