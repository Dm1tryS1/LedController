package com.example.connection_impl.presentation.choose_device.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.connection_impl.databinding.DropmenuConnectWifiDeviceByIpBinding
import com.example.core.utils.BottomSheetDialogBuilder

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