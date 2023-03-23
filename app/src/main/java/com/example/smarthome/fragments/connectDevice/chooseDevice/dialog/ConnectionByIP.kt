package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceByIpBinding
import com.example.smarthome.core.utils.BottomSheetDialogBuilder
import com.example.smarthome.core.utils.isIpAddress
import com.example.smarthome.core.utils.snackBar

object ConnectionByIP {
    fun create(
        id: Int,
        fragment: Fragment,
        connectAction: (id: Int, ip: String) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceByIpBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            connect.setOnClickListener {
                if ((ip.text.toString().isIpAddress())
                ) {
                    connectAction(id, ip.text.toString())
                } else {
                    fragment.snackBar(fragment.getString(R.string.connect_device_error_ip_format))
                }
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}