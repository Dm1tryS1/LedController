package com.example.ledcontroller.fragments.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ledcontroller.databinding.FragmentInformationBinding
import com.example.ledcontroller.fragments.information.recyclerView.InformationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val vm: InformationViewModel by viewModel()
    private lateinit var adapter: InformationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

    }

    private fun setUpRecycler() {
        adapter = InformationAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        layoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        vm.findSensor {
            adapter.info = it
        }
    }
}