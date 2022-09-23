package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuHumidityBinding
import com.example.ledcontroller.databinding.DropmenuTempetaruteBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder
import com.example.ledcontroller.utils.Command

object HumiditySensor {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int, Int>) -> Unit,
        data: String,
        date: String,
    ): Dialog {
        val binding = DropmenuHumidityBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            value.text = data
            this.date.text = date

            update.setOnClickListener {
                action(Command.GetHumidity.command)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}