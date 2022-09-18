package com.example.ledcontroller.fragments.information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ledcontroller.databinding.FragmentInformationBinding
import com.example.ledcontroller.fragments.information.data.Package
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

        binding.reload.setOnClickListener {
            vm.getInfo(15)
        }

        vm.startObserve().observe(activity as LifecycleOwner) { temp ->
            val newList = mutableListOf<Package>()
            adapter.info.forEach {
                if (it.id == temp.id)
                    newList.add(Package(temp.id, temp.date, temp.info))
                else
                    newList.add(it)
            }

            adapter.info = newList
        }
    }

    private fun setUpRecycler() {
        adapter = InformationAdapter(requireContext()) {
            vm.getInfo(it)
        }

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