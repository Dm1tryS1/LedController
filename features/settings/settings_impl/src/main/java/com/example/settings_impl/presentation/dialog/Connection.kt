package com.example.settings_impl.presentation.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.data.wifi.WifiInfo
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.settings_impl.databinding.DropmenuConnectBinding

object Connection {
    fun create(
        fragment: Fragment,
        connectAction: (wifiInfo: WifiInfo) -> Unit,
        wifiInfo: WifiInfo
    ): Dialog {
        val binding = DropmenuConnectBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            title.text = "${title.text} ${wifiInfo.ssid}"

            connect.setOnClickListener {
                connectAction(wifiInfo.copy(password = password.text.toString()))
                dialog.dismiss()
            }

            disconnect.setOnClickListener {
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}