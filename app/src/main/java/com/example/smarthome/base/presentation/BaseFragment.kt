package com.example.smarthome.base.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smarthome.common.navigation.BackPressConsumer
import com.example.smarthome.common.navigation.onBackPressed
import com.example.smarthome.utils.collectWhenStarted
import com.example.smarthome.utils.doOnCreate
import com.example.smarthome.utils.doOnStartStop

abstract class BaseFragment<State : Any, Event : Any> : Fragment(), BackPressConsumer {

    protected abstract val vm: ViewModelInterface<State, Event>

    override fun onBackPressed() = childFragmentManager.onBackPressed() || vm.onBackPressed()

    protected abstract fun renderState(state: State)

    protected abstract fun handleEvent(event: Event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.viewState.collectWhenStarted(this, ::renderState)
        vm.viewEvent.collectWhenStarted(this, ::handleEvent)

        lifecycle.doOnStartStop(onStart = vm::onViewActive, onStop = vm::onViewInactive)
        lifecycle.doOnCreate(vm::onCreate)

    }

}