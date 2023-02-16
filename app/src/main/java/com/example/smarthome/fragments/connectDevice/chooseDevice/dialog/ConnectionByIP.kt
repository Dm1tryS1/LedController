package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import android.util.Patterns
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceByIpBinding
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.utils.BottomSheetDialogBuilder
import com.example.smarthome.utils.isIpAddress
import com.example.smarthome.utils.snackBar
import java.util.regex.Pattern

object ConnectionByIP {
    fun create(
        type: Int,
        id: Int,
        fragment: Fragment,
        connectAction: (type: Int, id: Int, ip: String) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceByIpBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            connect.setOnClickListener {
                if ((ip.text.toString().isIpAddress())
                ) {
                    connectAction(type, id, ip.text.toString())
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