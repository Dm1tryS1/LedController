package com.example.smarthome.main

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.smarthome.common.device.ControlType
import com.example.smarthome.fragments.charts.ChartsFragment
import com.example.smarthome.fragments.connectDevice.ConnectDeviceFragment
import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceFragment
import com.example.smarthome.fragments.connectDevice.remoteControl.RemoteControlFragment
import com.example.smarthome.fragments.information.InformationFragment
import com.example.smarthome.fragments.main.MainFragment
import com.example.smarthome.fragments.system.SystemFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.parcelize.Parcelize

object Screens {
    fun InformationScreen() = FragmentScreen { InformationFragment() }
    fun MainScreen() = FragmentScreen { MainFragment() }
    fun ChartScreen(chartsParams: ChartsParams) =
        FragmentScreen { ChartsFragment::class.java.createScreen(chartsParams) }

    fun SystemScreen() = FragmentScreen { SystemFragment() }
    fun ConnectDeviceScreen() = FragmentScreen { ConnectDeviceFragment() }
    fun ChooseDeviceScreen(chooseDeviceParams: ChooseDeviceParams) =
        FragmentScreen { ChooseDeviceFragment::class.java.createScreen(chooseDeviceParams) }

    fun RemoteControlScreen() = FragmentScreen { RemoteControlFragment() }

    const val PARAMS = "PARAMS"

    private fun <T> Class<out Fragment>.createScreen(params: T) =
        this.newInstance().apply {
            arguments = bundleOf(PARAMS to params)
        }

}

@Parcelize
data class ChooseDeviceParams(val controlType: ControlType = ControlType.Connect) : Parcelable

@Parcelize
data class ChartsParams(val deviceType: Int, val id: Int) : Parcelable