package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuTempetaruteBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder
import com.example.smarthome.utils.Command

object TemperatureSensor {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int, Int>) -> Unit,
        data: String,
        date: String,
    ): Dialog {
        val binding = DropmenuTempetaruteBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            value.text = data
            this.date.text = date

            update.setOnClickListener {
                action(Command.GetTemperature.command)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}