package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceByIpBinding
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.utils.BottomSheetDialogBuilder

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
                connectAction(id, ip.text.toString())
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}