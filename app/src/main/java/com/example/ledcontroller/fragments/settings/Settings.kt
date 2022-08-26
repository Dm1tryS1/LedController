package com.example.ledcontroller.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ledcontroller.databinding.FragmentSettingsBinding
import com.example.ledcontroller.fragments.settings.recyclerView.DeviceAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class Settings : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: DeviceAdapter
    private val vm: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

        binding.reload.setOnClickListener {
            findDevices()
        }
    }

    private fun findDevices() {
        vm.findDevices {
            adapter.devices = it
            it.forEach {
                Log.d(it.name, it.address)
            }
        }
    }

    private fun setUpRecycler() {
        adapter = DeviceAdapter { address ->
            vm.connect(address) {
                if (it)
                    Toast.makeText(requireContext(), "Соединено",Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(requireContext(), "Ошибка",Toast.LENGTH_SHORT).show()
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        layoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        vm.findDevices {
            adapter.devices = it
        }
    }

}