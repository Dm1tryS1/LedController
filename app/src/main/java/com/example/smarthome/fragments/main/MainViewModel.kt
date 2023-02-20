package com.example.smarthome.fragments.main

import androidx.fragment.app.Fragment
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.fragments.home.Home
import com.example.smarthome.fragments.settings.Settings
import java.util.*

class MainViewModel: BaseViewModel<MainState, Unit>() {

    private val fragmentsStack = Stack<TabType>()

    init {
        fragmentsStack.add(TabType.Home)
    }

    override fun createInitialState(): MainState {
        return MainState(currentTab = fragmentsStack.peek())
    }

    fun onShowFragment(tab: TabType) {
        if (fragmentsStack.peek() == tab) {
            return
        }

        fragmentsStack.remove(tab)
        fragmentsStack.push(tab)

        updateState {
            it.copy(
                currentTab = tab,
            )
        }
    }

    fun buildFragment(type: TabType): Fragment {
        return when (type) {
            TabType.Home -> Home()
            TabType.Settings -> Settings()
        }
    }
}