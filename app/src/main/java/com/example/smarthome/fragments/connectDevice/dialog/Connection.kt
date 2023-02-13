package com.example.smarthome.fragments.connectDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        fragment: Fragment,
        connectAction: (ssid: String, password: String) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            connect.setOnClickListener {
                connectAction(ssid.text.toString(), password.text.toString())
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}