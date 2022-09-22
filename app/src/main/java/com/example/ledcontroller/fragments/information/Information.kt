package com.example.ledcontroller.fragments.information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.ledcontroller.R
import com.example.ledcontroller.databinding.FragmentInformationBinding
import com.example.ledcontroller.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.ledcontroller.fragments.information.recyclerView.model.InfoViewItem
import com.example.ledcontroller.fragments.information.recyclerView.util.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val vm: InformationViewModel by viewModel()
    private val adapter = InformationAdapter(onMenuClicked = { id, view -> vm.onMenuClicked(id, view) })

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
        binding.participants.adapter = adapter
        adapter.items = vm.findSensor()
        binding.participants.supportBottomSheetScroll()

        adapter.items.forEach {
            Log.d("here", it.toString())
        }

       // adapter.notifyDataSetChanged()

//        binding.reload.setOnClickListener {
//            vm.getInfo(15)
//        }

//        vm.startObserve().observe(activity as LifecycleOwner) { temp ->
//            val newList = mutableListOf<InfoViewItem>()
//            adapter.items.forEach {
//                if (it.id == temp.id)
//                    newList.add(InfoViewItem(R.drawable.ic_conditioner, temp.id!!, temp.date.toString(), temp.info.toString()))
//                else
//                    newList.add(it)
//            }
//
//            adapter.items = newList
//        }
    }
}