package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.data.wifi.WifiInfo
import com.example.core.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        id: Int,
        fragment: Fragment,
        wifiInfo: WifiInfo,
        connectAction: (id: Int, wifiInfo: WifiInfo) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            title.text = "${title.text} ${wifiInfo.ssid}"

            connect.setOnClickListener {
                connectAction(id, wifiInfo.copy(password = password.text.toString()))
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}