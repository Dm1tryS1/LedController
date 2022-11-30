package com.example.smarthome.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commitNow
import com.example.smarthome.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Main : BaseFragment<MainState, Unit>() {
    private lateinit var binding: FragmentMainBinding
    override val vm: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navView.setOnItemSelectedListener { item ->
            val tabType = TabsMapper.mapItemIdToTab(item) ?: return@setOnItemSelectedListener false
            vm.onShowFragment(tabType)
            true
        }
    }


    override fun renderState(state: MainState) {
        val tabToOpen = state.currentTab

        binding.navView.selectedItemId = TabsMapper.mapTabToItemId(tabToOpen)

        childFragmentManager.commitNow {
            childFragmentManager.fragments
                .filter { it.tag != tabToOpen.name }
                .forEach { detach(it) }

            val existedFragment = childFragmentManager.findFragmentByTag(tabToOpen.name)
            when {
                existedFragment == null -> {
                    val newFragment = vm.buildFragment(tabToOpen)
                    add(binding.content.id, newFragment, tabToOpen.name)
                }
                existedFragment.isDetached -> {
                    attach(existedFragment)
                }
            }
        }
    }

    override fun handleEvent(event: Unit) = Unit


}