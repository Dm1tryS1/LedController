package com.example.smarthome.base.presentation

import androidx.fragment.app.Fragment
import com.example.smarthome.common.navigation.BackPressConsumer
import com.example.smarthome.common.navigation.onBackPressed

abstract class BaseFragment : Fragment(), BackPressConsumer {

    protected abstract val vm: ViewModelInterface

    override fun onBackPressed() = childFragmentManager.onBackPressed() || vm.onBackPressed()

}