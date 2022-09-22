package com.example.ledcontroller.fragments.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.ledcontroller.databinding.FragmentInformationBinding
import com.example.ledcontroller.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.ledcontroller.utils.Command
import com.example.ledcontroller.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val vm: InformationViewModel by viewModel()
    private val adapter =
        InformationAdapter(onMenuClicked = { id, view -> vm.onMenuClicked(id, view) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        sensors.adapter = adapter
        sensors.supportBottomSheetScroll()

        vm.state.observe(activity as LifecycleOwner) { state ->
            if (state.data != null) {
                sensors.isVisible = true
                adapter.items = state.data
            } else {
                sensors.isVisible = false
            }
        }

        reload.setOnClickListener {
            vm.sendCommand(Command.BroadCast.command)
        }

        vm.getInfo()
        vm.initializeState()
    }
}