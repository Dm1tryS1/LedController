package com.example.ledcontroller.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Settings : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
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
        findDevices()

        binding.address.setOnClickListener {
            vm.connect(binding.address.text.toString()) {
                if (it)
                    Toast.makeText(requireContext(), "Успех!",Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(requireContext(), "Неуспех!",Toast.LENGTH_SHORT).show()
            }
        }

        binding.reload.setOnClickListener {
            findDevices()
        }
    }

    private fun findDevices() {
        vm.findDevices { map ->
            map.forEach{
                binding.address.text = it.value
            }
        }
    }
}