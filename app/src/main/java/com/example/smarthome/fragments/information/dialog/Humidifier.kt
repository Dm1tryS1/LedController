package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuHumidifierBinding
import com.example.smarthome.core.utils.BottomSheetDialogBuilder
import com.example.smarthome.common.device.HumidifierCommands

object Humidifier {
    fun create(
        fragment: Fragment,
        action: (command: String) -> Unit,
        on: Boolean
    ): Dialog {
        val binding = DropmenuHumidifierBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            offOn.setOnClickListener {
                if (on) {
                    action.invoke(HumidifierCommands.On.command)
                } else {
                    action.invoke(HumidifierCommands.Off.command)
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