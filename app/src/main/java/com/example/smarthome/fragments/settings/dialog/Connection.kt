package com.example.smarthome.fragments.settings.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        fragment: Fragment,
        connectAction: (address: String) -> Unit,
        disconnectAction: () -> Unit,
        address: String,
    ): Dialog {
        val binding = DropmenuConnectBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            connect.setOnClickListener {
                connectAction(address)
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