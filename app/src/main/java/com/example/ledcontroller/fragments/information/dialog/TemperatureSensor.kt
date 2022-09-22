package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuTempetaruteBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder

object TemperatureSensor {
    fun create(
        fragment: Fragment,
        action: (command: Int) -> Unit,
        data: String,
        date: String,
        command: Int,

    ): Dialog {
        val binding = DropmenuTempetaruteBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            value.text = data
            this.date.text = date

            update.setOnClickListener {
                action(command)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}