package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuConditioenerBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder
import com.example.ledcontroller.utils.Command

object Conditioner {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int,Int>) -> Unit,
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(Command.ConditionerOnOff.command)
            }

            reduce.setOnClickListener {
                action(Command.ConditionerReduceTemperature.command)
            }

            add.setOnClickListener {
               action(Command.ConditionerAddTemperature.command)
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}