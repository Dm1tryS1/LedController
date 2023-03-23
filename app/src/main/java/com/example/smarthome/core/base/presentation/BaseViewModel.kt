package com.example.smarthome.core.base.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.core.utils.kotlin.Activable
import com.example.smarthome.core.utils.kotlin.activableFlow
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<State : Any, Event : Any>(
    protected val router:Router
) : ViewModel(), ViewModelInterface<State, Event> {

    private val _viewState by lazy { MutableStateFlow(createInitialState()) }
    override val viewState get() = _viewState.asStateFlow()

    private val _viewEvent = Channel<Event>(Channel.UNLIMITED)
    override val viewEvent = _viewEvent.receiveAsFlow()

    protected val currentViewState: State get() = viewState.value

    protected abstract fun createInitialState(): State

    protected fun updateState(update: (State) -> State) {
        _viewState.update(update)
    }

    protected fun sendEvent(event: Event) {
        _viewEvent.trySend(event)
    }

    override fun onBackPressed(): Boolean = false

    private val viewActivable = Activable()

    @CallSuper
    override fun onViewActive() {
        viewActivable.onActive()
    }

    @CallSuper
    override fun onViewInactive() {
        viewActivable.onInactive()
    }

    @CallSuper
    override fun onCreate() = Unit

    protected fun <T> Flow<T>.collectWhenViewActive() =
        activableFlow(originalFlow = this, activable = viewActivable, scope = viewModelScope)
}