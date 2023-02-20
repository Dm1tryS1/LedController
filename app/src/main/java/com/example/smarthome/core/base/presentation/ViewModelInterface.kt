package com.example.smarthome.core.base.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ViewModelInterface<out S : Any, out E : Any> {
    val viewState: StateFlow<S>

    val viewEvent: Flow<E>

    fun onBackPressed(): Boolean

    fun onViewActive()
    fun onViewInactive()
    fun onCreate()
}