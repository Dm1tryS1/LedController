package com.example.smarthome.common.navigation

import androidx.fragment.app.FragmentManager

interface BackPressConsumer {

    fun onBackPressed(): Boolean

}

internal fun FragmentManager.onBackPressed(): Boolean =
    fragments.any { it is BackPressConsumer && it.onBackPressed() }