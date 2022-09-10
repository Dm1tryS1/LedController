package com.example.ledcontroller.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.FragmentHomeBinding
import com.example.ledcontroller.main.MainActivity
import com.example.ledcontroller.main.Screen
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val vm: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOn.setOnClickListener {
            vm.sendData("1") {
                if (!it)
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnOff.setOnClickListener {
            vm.sendData("2") {
                if (!it)
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()

            }
        }

//        binding.drawMode.setOnClickListener {
//            (activity as MainActivity).navigate(Screen.Table)
//        }

        binding.informationMode.setOnClickListener {
            (activity as MainActivity).navigate(Screen.Information)
        }
    }
}