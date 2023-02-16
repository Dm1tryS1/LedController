package com.example.smarthome.utils

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private class FlowWhenStartedObserver<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit,
    private val collectLatest: Boolean = false,
) {

    private var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    job = source.lifecycleScope.launch {
                        if (collectLatest) {
                            flow.collectLatest(collector)
                        } else {
                            flow.collect {
                                collector.invoke(it)
                            }
                        }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                    job = null
                }
                else -> Unit
            }
        })
    }
}

fun <T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    collector: suspend (T) -> Unit,
) {
    FlowWhenStartedObserver(lifecycleOwner, this, collector)
}

fun <T> Flow<T>.collectLatestWhenStarted(
    lifecycleOwner: LifecycleOwner,
    collector: suspend (T) -> Unit,
) {
    FlowWhenStartedObserver(lifecycleOwner, this, collector, true)
}

fun Lifecycle.doOnStartStop(
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            onStart()
        }

        override fun onStop(owner: LifecycleOwner) {
            onStop()
        }
    })
}

fun Lifecycle.doOnDestroy(action: () -> Unit) {
    addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            action()
        }
    })
}

fun Lifecycle.doOnCreate(action: () -> Unit) {
    addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            action()
        }
    })
}