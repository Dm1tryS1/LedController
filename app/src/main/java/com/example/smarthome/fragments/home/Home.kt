package com.example.smarthome.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentHomeBinding
import com.example.smarthome.core.utils.snackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : BaseFragment<Unit, HomeEvent>() {

    private lateinit var binding: FragmentHomeBinding
    override val vm: HomeViewModel by viewModel()

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
        binding.informationMode.setOnClickListener {
            vm.checkConnection()
        }
    }


    override fun handleEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.NoConnectionEvent -> snackBar(getString(R.string.error_connection))
        }
    }

    override fun renderState(state: Unit) = Unit


}