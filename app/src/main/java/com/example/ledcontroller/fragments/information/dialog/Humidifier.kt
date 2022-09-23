package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuHumidifierBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder
import com.example.ledcontroller.utils.Command

object Humidifier {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int,Int>) -> Unit,
    ): Dialog {
        val binding = DropmenuHumidifierBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(Command.HumidifierOnOff.command)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}