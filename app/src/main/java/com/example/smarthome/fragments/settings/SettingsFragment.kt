package com.example.smarthome.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.fragmentViewBinding
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.databinding.FragmentSettingsBinding
import com.example.smarthome.fragments.settings.dialog.Connection
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>(R.layout.fragment_settings) {

    private val binding by fragmentViewBinding(FragmentSettingsBinding::bind)

    override val vm: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        connect.setOnClickListener {
            vm.onConnectClicked()
        }

        wifi.setOnClickListener {
            vm.onWifiClicked()
        }
    }

    override fun renderState(state: SettingsState) {
        binding.loader.isVisible = state.isLoading
        binding.icon.isVisible = !state.isLoading
        binding.title.isVisible = !state.isLoading
    }

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ShowSnack -> snackBar(getString(event.message))
            is SettingsEvent.OpenDialog -> Connection.create(
                fragment = this@SettingsFragment,
                connectAction = vm::connect,
                wifiInfo = event.wifiInfo
            ).show()
        }
    }

}