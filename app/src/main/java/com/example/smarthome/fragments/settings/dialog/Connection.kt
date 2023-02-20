package com.example.smarthome.fragments.settings.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectBinding
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.core.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        fragment: Fragment,
        connectAction: (address: String, wifiInfo: WifiInfo) -> Unit,
        disconnectAction: () -> Unit,
        address: String,
        wifiInfo: WifiInfo
    ): Dialog {
        val binding = DropmenuConnectBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            title.text = "${title.text} ${wifiInfo.ssid}"

            connect.setOnClickListener {
                connectAction(address, wifiInfo.copy(password = password.text.toString()))
                dialog.dismiss()
            }

            disconnect.setOnClickListener {
                disconnectAction()
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}