package com.example.smarthome.main

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.smarthome.common.device.ControlType
import com.example.smarthome.common.device.SensorType
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
    fun informationScreen() = InformationFragment::class.java.createScreen(null)
    fun mainScreen() = MainFragment::class.java.createScreen(null)
    fun chartScreen(chartsParams: ChartsParams) =
        ChartsFragment::class.java.createScreen(chartsParams)

    fun systemScreen() = SystemFragment::class.java.createScreen(null)
    fun connectDeviceScreen() = ConnectDeviceFragment::class.java.createScreen(null)
    fun chooseDeviceScreen(chooseDeviceParams: ChooseDeviceParams) =
        ChooseDeviceFragment::class.java.createScreen(chooseDeviceParams)

    fun remoteControlScreen() = RemoteControlFragment::class.java.createScreen(null)

    const val PARAMS = "PARAMS"

    private fun <T : Parcelable> Class<out Fragment>.createScreen(params: T?) =
        FragmentScreen {
            if (params != null) {
                this.newInstance().apply {
                    arguments = bundleOf(PARAMS to params)
                }
            } else {
                this.newInstance()
            }
        }

}

@Parcelize
data class ChooseDeviceParams(val controlType: ControlType = ControlType.Connect) : Parcelable

@Parcelize
data class ChartsParams(val deviceType: SensorType, val id: Int) : Parcelable