package com.example.core.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.core.navigation.BackPressConsumer
import com.example.core.navigation.onBackPressed
import com.example.core.utils.collectWhenStarted
import com.example.core.utils.doOnCreate
import com.example.core.utils.doOnStartStop
import com.example.core.navigation.Const

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
        val params = arguments?.getParcelable(Const.PARAMS) as T?
        return if (params != null && params::class.java == clazz) {
            params
        } else {
            null
        }
    }
}