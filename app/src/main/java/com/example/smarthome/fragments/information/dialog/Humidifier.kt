package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuHumidifierBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.CommandForHumidifier

object Humidifier {
    fun create(
        fragment: Fragment,
        action: (aPackage: Command) -> Unit,
        id: Int
    ): Dialog {
        val binding = DropmenuHumidifierBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(Command.Humidifier(id, CommandForHumidifier.OnOff))
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}