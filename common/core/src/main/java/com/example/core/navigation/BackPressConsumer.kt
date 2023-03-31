package com.example.core.navigation

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface BackPressConsumer {

    fun onBackPressed(): Boolean

}

internal fun FragmentManager.onBackPressed(): Boolean =
    fragments.any { it is BackPressConsumer && it.onBackPressed() }

fun <T : Parcelable> Class<out Fragment>.createScreen(params: T) =
    FragmentScreen {
        if (params !is NoParams) {
            this.newInstance().apply {
                arguments = bundleOf(Const.PARAMS to params)
            }
        } else {
            this.newInstance()
        }
    }

object Const {
    const val PARAMS = "PARAMS"
}