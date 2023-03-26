package com.example.smarthome.core.base.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.smarthome.common.navigation.BackPressConsumer
import com.example.smarthome.common.navigation.onBackPressed
import com.example.smarthome.core.utils.collectWhenStarted
import com.example.smarthome.core.utils.doOnCreate
import com.example.smarthome.core.utils.doOnStartStop
import com.example.smarthome.main.Screens

abstract class BaseFragment<State : Any, Event : Any> : Fragment, BackPressConsumer {

    protected abstract val vm: ViewModelInterface<State, Event>

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

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

    fun <T : Parcelable> getParams(clazz: Class<T>): T? {
        val params = arguments?.getParcelable(Screens.PARAMS) as T?
        return if (params != null && params::class.java == clazz) {
            params
        } else {
            null
        }
    }
}