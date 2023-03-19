package com.example.smarthome.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.databinding.FragmentSettingsBinding
import com.example.smarthome.fragments.settings.dialog.Connection
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>() {

    private lateinit var binding: FragmentSettingsBinding

    override val vm: SettingsViewModel by viewModel()

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
            is SettingsEvent.ConnectionSuccessEvent -> snackBar(getString(R.string.settings_connected))
            is SettingsEvent.ConnectionFailureEvent -> snackBar(getString(R.string.settings_fail))
            is SettingsEvent.Error -> snackBar(getString(event.message))
            is SettingsEvent.OnItemClickedEvent -> Connection.create(
                fragment = this@SettingsFragment,
                connectAction = vm::connect,
                wifiInfo = event.wifiInfo
            ).show()
        }
    }

}