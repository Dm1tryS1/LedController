package com.example.smarthome.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commitNow
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.core.utils.fragmentViewBinding
import com.example.smarthome.core.utils.snackBar
import com.example.smarthome.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainState, MainEvent>(R.layout.fragment_main) {

    private val binding by fragmentViewBinding(FragmentMainBinding::bind)

    override val vm: MainViewModel by viewModel()

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

    override fun handleEvent(event: MainEvent) {
        when(event){
            is MainEvent.ShowSnack -> snackBar(getString(R.string.main_double_click_exit))
        }
    }



}