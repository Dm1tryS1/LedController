package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        id: Int,
        fragment: Fragment,
        connectAction: (id: Int, ssid: String, password: String) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            connect.setOnClickListener {
                connectAction(id, ssid.text.toString(), password.text.toString())
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}