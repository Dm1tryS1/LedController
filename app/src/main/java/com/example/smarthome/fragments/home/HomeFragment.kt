package com.example.smarthome.fragments.home

import android.os.Bundle
import android.view.View
import com.example.smarthome.R
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.smarthome.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<Unit, Unit>(R.layout.fragment_home) {

    private val binding by fragmentViewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.informationMode.setOnClickListener {
            vm.openInformationFragment()
        }
    }
    override fun renderState(state: Unit) = Unit
    override fun handleEvent(event: Unit) = Unit

}